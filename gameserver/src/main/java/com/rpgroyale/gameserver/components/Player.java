package com.rpgroyale.gameserver.components;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

public class Player {

    private String name;

    private String session;

    private int xp;

    private int level;

    private int diamonds;

    private int wins;

    private int losses;

    private double avgplace;

    private int elo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public double getAvgplace() {
        return avgplace;
    }

    public void setAvgplace(double avgplace) {
        this.avgplace = avgplace;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getDiamonds() {
        return diamonds;
    }

    public void setDiamonds(int diamonds) {
        this.diamonds = diamonds;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }



    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }
}
