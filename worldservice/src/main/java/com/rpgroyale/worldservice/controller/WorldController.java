package com.rpgroyale.worldservice.controller;

import com.rpgroyale.worldservice.entity.TileEvent;
import com.rpgroyale.worldservice.entity.WorldInfo;
import com.rpgroyale.worldservice.repo.TileEventRepo;
import com.rpgroyale.worldservice.repo.WorldInfoRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/world")
public class WorldController {

    @Autowired
    private WorldInfoRepo worldInfoRepo;

    @Autowired
    private TileEventRepo tileEventRepo;

    @GetMapping("/events/{gameId}")
    public ResponseEntity<List<TileEvent>> getAllEvents(@PathVariable int gameId) {
        log.info("Pulling all events for game: " + gameId);
        List<TileEvent> events = tileEventRepo.findAllByGameId(gameId);
        log.info("events object contains: " + events.toString());
        if (events == null) {
            log.error("Could not find any events for gameId: " + gameId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<WorldInfo> getWorldInfoForGame(@PathVariable int gameId) {
        log.info("Getting world info for game: " + gameId);
        WorldInfo worldInfo = worldInfoRepo.findByGameId(gameId);
        if (worldInfo == null) {
            log.error("World for game id " + gameId + " not found.");
            WorldInfo worldInfoNew = new WorldInfo();
            worldInfoNew.setGameId(99);
            worldInfoNew.setHeight(300);
            worldInfoNew.setWidth(300);
            worldInfoNew.setSeed(54);
            return new ResponseEntity<>(worldInfoNew, HttpStatus.OK);
        }

        return new ResponseEntity<>(worldInfo, HttpStatus.OK);
    }
}
