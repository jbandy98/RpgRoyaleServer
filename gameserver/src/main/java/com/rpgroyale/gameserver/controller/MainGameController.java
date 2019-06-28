package com.rpgroyale.gameserver.controller;

import com.rpgroyale.gameserver.loops.MainGameLoop;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/game")
public class MainGameController {

    @RequestMapping("/startgame/{gameId}")
    void startNewGame(@PathVariable("gameId") Integer gameId) {
        log.info("Creating new game instance with id " + gameId + "...");
        MainGameLoop mainGameLoop = new MainGameLoop(gameId);
        mainGameLoop.start();
    }
}
