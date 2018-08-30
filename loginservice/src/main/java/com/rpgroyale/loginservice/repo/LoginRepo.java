package com.rpgroyale.loginservice.repo;

import com.rpgroyale.loginservice.entity.Login;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoginRepo extends CrudRepository<Login, String> {

    public List<Login> findByUser(String username);
}
