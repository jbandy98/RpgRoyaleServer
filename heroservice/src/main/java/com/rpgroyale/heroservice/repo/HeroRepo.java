package com.rpgroyale.heroservice.repo;

import com.rpgroyale.heroservice.entity.Hero;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeroRepo extends CrudRepository<Hero, String> {

    public Hero findByheroId(int heroId);
    public List<Hero> findByGameId(int gameId);
    public List<Hero> findByPlayerName(String playerName);

}
