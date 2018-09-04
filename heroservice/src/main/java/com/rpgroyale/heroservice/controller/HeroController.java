package com.rpgroyale.heroservice.controller;


import com.rpgroyale.heroservice.entity.Hero;
import com.rpgroyale.heroservice.repo.HeroRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value="/hero")
public class HeroController {

    @Autowired
    HeroRepo heroRepo;

    @GetMapping(value="/id/{heroId}")
    public ResponseEntity<Hero> getHero(@PathVariable int heroId) {
        Hero hero = heroRepo.findByheroId(heroId);
        if (hero == null) {
            log.error("Did not find a matching hero for hero id: " + heroId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("Returning hero information for heroId: " + heroId);
        return new ResponseEntity<>(hero, HttpStatus.OK);
    }

    @GetMapping(value="/user/{playerName}")
    public ResponseEntity<List<Hero>> getHeroesByPlayer(@PathVariable String playerName) {
        List<Hero> heroes = heroRepo.findByPlayerName(playerName);
        if (heroes == null) {
            log.error("Did not find any heroes listed for player: " + playerName);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        log.info("Returning all heros for player: " + playerName);
        return new ResponseEntity<>(heroes, HttpStatus.OK);
    }

    @GetMapping(value="/game/{gameId}")
    public ResponseEntity<List<Hero>> getHeroesByGameId(@PathVariable int gameId) {
        List<Hero> heroes = heroRepo.findByGameId(gameId);
        if (heroes == null) {
            log.error("Did not find any heroes listed for game: " + gameId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("Returning all heroes in gameId: " + gameId);
        return new ResponseEntity<>(heroes, HttpStatus.OK);
    }

    @PostMapping(value="/save")
    public ResponseEntity<Hero> saveHero(@RequestBody Hero hero) {
        if (heroRepo.findByheroId(hero.getHeroId()) != null) {
            log.info("Updating heroId" + hero.getHeroId());

        }
        Hero newHero = heroRepo.save(hero);
        log.info("Creating hero with heroId: " + newHero.getHeroId());

        return new ResponseEntity<>(newHero, HttpStatus.OK);
    }
}
