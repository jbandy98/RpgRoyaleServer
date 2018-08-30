package com.rpgroyale.loginservice.controller;

import com.rpgroyale.loginservice.entity.CreateUserResponse;
import com.rpgroyale.loginservice.entity.Login;
import com.rpgroyale.loginservice.entity.User;
import com.rpgroyale.loginservice.repo.LoginRepo;
import com.rpgroyale.loginservice.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private LoginRepo loginRepo;

    @PostMapping(value="/login")
    public ResponseEntity<Login> ValidateLogin(@RequestBody final User user) {
        log.info("Validating login for " + user.getUsername());
        Login loginResult = new Login();
        User result = null;
        try {
            result = userRepo.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        } catch (Exception e) {
            loginResult.setStatus("FAILED");
        }

        if (result == null) {
            log.error("No user found with these credentials.");
            loginResult.setStatus("BADCREDENTIALS");
        } else {
            log.info("Returning a user object - found " + result.getUsername());
            StringBuffer sessionStr = new StringBuffer();
            for (int i = 0; i < 20; i++) {
                int randomNum = ThreadLocalRandom.current().nextInt(33,126+1);
                char ch = (char)randomNum;
                sessionStr.append(ch);
            }
            log.info("Session id generated for " + result.getUsername() + ": " + sessionStr);
            loginResult.setUser(result.getUsername());
            loginResult.setStatus("OK");
            loginResult.setActive(true);
            loginResult.setSessionId(sessionStr.toString());
            loginRepo.save(loginResult);

            log.info("Saved login info to db.");
            List<Login> logins = loginRepo.findByUser(loginResult.getUser());
            log.info("These entries are in db for this user: ");
            for (Login logInfo : logins) {
                log.info(logInfo.toString());
            }
        }

        return new ResponseEntity<>(loginResult, HttpStatus.OK);
    }

    @PostMapping(value="/create")
    public ResponseEntity<CreateUserResponse> CreateUser(@RequestBody final User user) {
        CreateUserResponse response = new CreateUserResponse();

        // first make sure this username isn't already in use
        User dupeUser = userRepo.findByUsername(user.getUsername());

        if (dupeUser != null) {
            response.setUser(user.getUsername());
            response.setStatus("DUPLICATE");
            log.info("Duplicate username found.");
        } else {
            log.info("Creating a new user: " + user.getUsername());
            User result = null;
            try {
                result = userRepo.save(user);
            } catch (Exception e) {
                log.error("Error creating username - " + user.getUsername());
                return null;
            }

            log.info("User successfully created.");

            response.setUser(result.getUsername());
            response.setStatus("SUCCESS");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value="/hello")
    public String hello(@RequestParam(name="name", required=false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "hello";
    }
}
