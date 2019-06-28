package com.rpgroyale.gameserver.loops;

import com.rpgroyale.gameserver.clients.ClientUtil;
import com.rpgroyale.gameserver.components.Encounter;
import com.rpgroyale.gameserver.components.GameData;
import com.rpgroyale.gameserver.components.Hero;
import com.rpgroyale.gameserver.components.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MainGameLoop extends Thread {

    Integer gameId;
    int UPS = 1;
    boolean running;


    // game components
    // enemies
    List<Encounter> encounters;

    // gamedata - player loc in game
    List<GameData> gamePlayers;

    // players - main player profile
    List<Player> players;

    // heroes
    List<Hero> heroes;

    // event items


    public MainGameLoop(Integer gameId) {
        this.gameId = gameId;

    }

    @Override
    public void run() {
        long initialTime = System.nanoTime();
        final double timeU = 1000000000 / UPS;
        double deltaU = 0;
        long currentTime;
        loadAllData();
        running = true;

        while(running) {
            currentTime = System.nanoTime();
            deltaU += (currentTime - initialTime) / timeU;
            initialTime = currentTime;

            if (deltaU >= 6) {
                runUpdate();
                deltaU = 0;
            }
        }
    }

    void runUpdate() {
        // game updates occur here
        log.info("running game updates for gameId: " + this.gameId);
        ClientUtil.encounterClient.moveEncounters(this.gameId);
    }

    void loadAllData() {
        ClientUtil.encounterClient.createEncounters(this.gameId);
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }
}
