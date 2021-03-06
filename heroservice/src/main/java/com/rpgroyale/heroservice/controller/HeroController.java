package com.rpgroyale.heroservice.controller;

import com.rpgroyale.heroservice.clients.JobClient;
import com.rpgroyale.heroservice.entity.Hero;
import com.rpgroyale.heroservice.entity.JobLevel;
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

    @Autowired
    JobClient jobClient;

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

    @GetMapping(value="/cleanup/{playerId}")
    public ResponseEntity<Hero> cleanupPlayerHeroes(@PathVariable String playerId) {
        log.info("Running cleanup script for " + playerId);
        List<Hero> heroes = heroRepo.findByPlayerName(playerId);
        for(Hero hero : heroes) {
            log.info("Cleaning up hero " + hero.getHeroName() + " for player " + playerId);
            heroRepo.delete(hero);
        }
        heroes = heroRepo.findByPlayerName(playerId);

        if (heroes.isEmpty()) {
            log.info("Hero array is empty, returning ok.");
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            log.error("Hero array is NOT empty. Something didn't work correctly?");
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping(value="/create")
    public ResponseEntity<Hero> createHero(@RequestBody final Hero hero) {
        log.info("Creating a hero.");
        // we are good, this hero doesn't exist
        // pull the base stats out of the job level tables
        String className = hero.getClassName();
        JobLevel jobInfo = jobClient.getJobLevelInfo(className, 1);
        log.info("Job level data pulled. ");
        log.info("Job data pulled: " + jobInfo.getJobName() + " lvl " + jobInfo.getLevel());
        // save the data to the hero table
        Hero createdHero = new Hero();
        createdHero.setHeroName(hero.getHeroName());
        createdHero.setLevel(1);
        createdHero.setClassName(hero.getClassName());
        createdHero.setBaseStrength(jobInfo.getBaseStrength());
        createdHero.setBaseDexterity(jobInfo.getBaseDexterity());
        createdHero.setBaseSpeed(jobInfo.getBaseSpeed());
        createdHero.setBaseEndurance(jobInfo.getBaseEndurance());
        createdHero.setBaseSpirit(jobInfo.getBaseSpirit());
        createdHero.setBaseIntelligence(jobInfo.getBaseIntelligence());
        createdHero.setBaseWillpower(jobInfo.getBaseWillpower());
        createdHero.setBaseCharisma(jobInfo.getBaseCharisma());
        createdHero.setBaseHp(jobInfo.getBaseHp());
        createdHero.setBaseSp(jobInfo.getBaseSp());
        createdHero.setCurrentHp(jobInfo.getBaseHp());
        createdHero.setCurrentSp(jobInfo.getBaseSp());
        createdHero.setAttackRange(jobInfo.getAttackRange());
        createdHero.setAttackType(jobInfo.getAttackType());
        createdHero.setPlayerName(hero.getPlayerName());

        Hero savedHero = heroRepo.save(createdHero);

        // pass the hero back to the client with base stats
        return new ResponseEntity<>(savedHero, HttpStatus.OK);
    }
}
