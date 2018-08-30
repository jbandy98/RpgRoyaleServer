package com.rpgroyale.loginservice.repo;

import com.rpgroyale.loginservice.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, String> {

    public User findByUsernameAndPassword(String username, String password);
    public User findByUsername(String username);
}
