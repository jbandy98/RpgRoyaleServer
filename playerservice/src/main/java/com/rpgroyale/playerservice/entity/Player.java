package com.rpgroyale.playerservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Player {

    @Id
    private String name;

    @Column
    private String session;

    @Column
    private int xp;

    @Column
    private int level;

    @Column
    private int diamonds;

    @Column
    private int wins;

    @Column
    private int losses;

    @Column
    private double avgplace;

    @Column
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
