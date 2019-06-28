package com.rpgroyale.combatserver.clients;

import com.rpgroyale.combatserver.entities.Hero;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Component
@FeignClient(name="heroservice", path="/hero")
public interface HeroClient {

    @GetMapping(value="/user/{playerName}")
    List<Hero> getHeroesByPlayer(@PathVariable("playerName") String playerName);
}
