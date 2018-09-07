package com.rpgroyale.worldservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(TileEventKey.class)
public class TileEvent {

    @Id
    @Column(name="game_id")
    private int gameId;
    @Id
    @Column(name="event_id")
    private int eventId;            // lookup eventId in Event service
    @Column(name="x")
    private int x;
    @Column(name="y")
    private int y;
    @Column(name="event_type")
    private String eventType;       // event type determines which logic/subclass to use


    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
