package com.jbcomputers.enemyservice.controller;

import com.jbcomputers.enemyservice.entity.Encounter;
import com.jbcomputers.enemyservice.services.GameEnemyManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/enemy")
public class EnemyDataController {

    @Autowired
    GameEnemyManager manager;

    // get data for a single encounter
    @RequestMapping("/{gameId}/{encounterId}")
    ResponseEntity<Encounter> getEncounterDataForGame(@PathVariable("gameId") Integer gameId, @PathVariable("encounterId") Integer encounterId) {
        Encounter encounter = manager.getGameEncounter(gameId, encounterId);

        return new ResponseEntity<>(encounter, HttpStatus.OK);
    }

    // get encounter data
    @RequestMapping("/{gameId}")
    ResponseEntity<List<Encounter>> getAllEncounterDataForGame(@PathVariable("gameId") Integer gameId) {
        List<Encounter> encounterList = manager.getGameEncounterList(gameId);

        return new ResponseEntity<>(encounterList, HttpStatus.OK);
    }

    // clean up encounter
    @RequestMapping("/delete/{encounterId}")
    ResponseEntity deleteEncounterById(@PathVariable("encounterId") Integer encounterId) {
        manager.removeEncounterFromGame(encounterId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/createEncounters/{gameId}")
    ResponseEntity<List<Encounter>> createEncounters(@PathVariable("gameId") Integer gameId) {
        log.info("Testing the create encounter routine.");
        List<Encounter> encounterList = manager.updateEncounterList(gameId);
        return new ResponseEntity<>(encounterList, HttpStatus.OK);
    }

    @RequestMapping("/moveEncounters/{gameId}")
    ResponseEntity<List<Encounter>> moveEncounters(@PathVariable("gameId") Integer gameId) {
        List<Encounter> encounterList = manager.moveEncounters(gameId);
        return new ResponseEntity<>(encounterList, HttpStatus.OK);
    }

}
