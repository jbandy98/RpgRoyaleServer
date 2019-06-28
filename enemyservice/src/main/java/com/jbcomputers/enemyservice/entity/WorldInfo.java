package com.jbcomputers.enemyservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class WorldInfo {

    @Id
    @Column(name="game_id")
    private int gameId;        // each world needs an id to link back to a game
    @Column(name="width")
    private int width;          // the width of the game world (default 300)
    @Column(name="height")
    private int height;         // the height of the game world (default 300)
    @Column(name="seed")
    private int seed;           // a seed will give you the same procedurally generated world if used

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

}
