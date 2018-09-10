package com.rpgroyale.gamedataservice.controller;


import com.rpgroyale.gamedataservice.entity.GameData;
import com.rpgroyale.gamedataservice.repo.GameDataRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/game")
public class GameDataController {

    @Autowired
    private GameDataRepo gameDataRepo;

    @GetMapping(value="/{gameId}")
    public ResponseEntity<List<GameData>> getAllGameDataById(@PathVariable int gameId) {
        log.info("Getting list of game data for game id: " + gameId);
        List<GameData> gameData = gameDataRepo.findAllByGameId(gameId);

        if (gameData == null) {
            log.error("No game data found for game id: " + gameId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(gameData, HttpStatus.OK);
    }

    @GetMapping(value="/{gameId}/{playerId}")
    public ResponseEntity<GameData> getGameDataByGameIdAndPlayerId(@PathVariable int gameId, @PathVariable String playerId) {
        log.info("Getting game data for player " + playerId + " and game id " + gameId);
        GameData gameData = gameDataRepo.findByPlayerIdAndGameId(playerId, gameId);

        if (gameData == null) {
            log.error("No player data for player name " + playerId + " and game id " + gameId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(gameData, HttpStatus.OK);
    }

    @GetMapping(value="/player/{playerId}")
    public ResponseEntity<GameData> getGameDataByPlayerId(@PathVariable String playerId) {
        log.info("Getting game data for player " + playerId);
        GameData gameData = gameDataRepo.findByPlayerId(playerId);
        if (gameData == null) {
            log.error("Couldn't find any gamedata for player " + playerId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(gameData, HttpStatus.OK);
    }

    @PostMapping(value="/update")
    public ResponseEntity<GameData> updateGameInfo(@RequestBody final GameData gameData) {
        log.info("Updating game data for game id " + gameData.getGameId());
        // do any checks to validate this data

        // save the data to the db
        try {
            gameDataRepo.save(gameData);
        } catch (Exception e) {
            log.error("Saving game data failed. " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(gameData, HttpStatus.OK);
    }
}
