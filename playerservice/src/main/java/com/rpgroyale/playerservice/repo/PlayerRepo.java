package com.rpgroyale.playerservice.repo;

import com.rpgroyale.playerservice.entity.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepo extends CrudRepository<Player, String> {

    public Player findByName(String name);
}
