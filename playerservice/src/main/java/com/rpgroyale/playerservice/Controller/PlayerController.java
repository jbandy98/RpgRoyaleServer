package com.rpgroyale.playerservice.Controller;

import com.rpgroyale.playerservice.entity.Player;
import com.rpgroyale.playerservice.repo.PlayerRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    PlayerRepo playerRepo;

    @GetMapping("/{name}")
    ResponseEntity<Player> GetPlayerInfo(@PathVariable String name) {
        Player player = playerRepo.findByName(name);
        if (player == null) {
            log.error("Player not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @GetMapping("/create/{name}")
    ResponseEntity<Player> CreatePlayerInfo(@PathVariable String name) {
        Player player = new Player();
        player.setName(name);
        player.setDiamonds(50);
        player.setElo(1000);
        player.setWins(0);
        player.setLosses(0);
        player.setLevel(1);
        player.setXp(0);
        player.setAvgplace(10.0);
        playerRepo.save(player);

        return new ResponseEntity<>(player, HttpStatus.OK);
    }
}
