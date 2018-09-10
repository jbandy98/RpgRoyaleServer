package com.rpgroyale.gamedataservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(GameDataKey.class)
public class GameData implements Serializable {

    @Id
    @Column(name="player_id")
    private String playerId;

    @Id
    @Column(name="game_id")
    private int gameId;

    @Column(name="game_state")
    private String gameState;

    @Column(name="lava_timer")
    private float lavaTimer;

    @Column
    private int gold;

    @Column(name="loc_x")
    private int locX;

    @Column(name="loc_y")
    private int locY;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }

    public float getLavaTimer() {
        return lavaTimer;
    }

    public void setLavaTimer(float lavaTimer) {
        this.lavaTimer = lavaTimer;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getLocX() {
        return locX;
    }

    public void setLocX(int locX) {
        this.locX = locX;
    }

    public int getLocY() {
        return locY;
    }

    public void setLocY(int locY) {
        this.locY = locY;
    }
}
