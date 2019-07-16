package com.rpgroyale.combatserver.entities;

import javax.persistence.*;

@Entity
@Table(name="loot_data")
public class LootData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="loot_id")
    Integer lootId;

    @Column(name="player_id")
    String playerId;

    @Column(name="game_id")
    Integer gameId;

    @Column(name="encounter_id")
    Integer encounterId;

    @Column(name="xp_earned")
    Integer xpEarned;

    @Column(name="gp_earned")
    Integer gpEarned;

    @Column(name="ap_earned")
    Integer apEarned;

    public Integer getLootId() {
        return lootId;
    }

    public void setLootId(Integer lootId) {
        this.lootId = lootId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(Integer encounterId) {
        this.encounterId = encounterId;
    }

    public Integer getXpEarned() {
        return xpEarned;
    }

    public void setXpEarned(Integer xpEarned) {
        this.xpEarned = xpEarned;
    }

    public Integer getGpEarned() {
        return gpEarned;
    }

    public void setGpEarned(Integer gpEarned) {
        this.gpEarned = gpEarned;
    }

    public Integer getApEarned() {
        return apEarned;
    }

    public void setApEarned(Integer apEarned) {
        this.apEarned = apEarned;
    }
}
