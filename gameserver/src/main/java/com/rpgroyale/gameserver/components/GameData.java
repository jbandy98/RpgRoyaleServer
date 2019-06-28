package com.rpgroyale.gameserver.components;

import java.io.Serializable;

public class GameData {

    private String playerId;

    private int gameId;

    private String gameState;

    private float lavaTimer;

    private int gold;

    private int locX;

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
