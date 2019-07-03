package com.rpgroyale.combatserver.messagedata;

import com.rpgroyale.combatserver.entities.*;

import java.util.List;

public class CombatData {
    Integer gameId;
    Integer encounterId;
    PlayerGameData playerData;
    List<HeroUnit> heroUnits;
    List<EnemyUnit> enemyUnits;
    List<CombatUnit> allUnits;
    CombatGrid combatGrid;
    int gpEarned;
    int xpEarned;
    int apEarned;
    String combatState;

    public CombatData(Integer gameId, Integer encounterId, PlayerGameData playerData, List<HeroUnit> heroUnits, List<EnemyUnit> enemyUnits, List<CombatUnit> allUnits, CombatGrid combatGrid, int gpEarned, int xpEarned, int apEarned, String combatState) {
        this.gameId = gameId;
        this.encounterId = encounterId;
        this.playerData = playerData;
        this.heroUnits = heroUnits;
        this.enemyUnits = enemyUnits;
        this.allUnits = allUnits;
        this.combatGrid = combatGrid;
        this.gpEarned = gpEarned;
        this.xpEarned = xpEarned;
        this.apEarned = apEarned;
        this.combatState = combatState;
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

    public PlayerGameData getPlayerData() {
        return playerData;
    }

    public void setPlayerData(PlayerGameData playerData) {
        this.playerData = playerData;
    }

    public List<HeroUnit> getHeroUnits() {
        return heroUnits;
    }

    public void setHeroUnits(List<HeroUnit> heroUnits) {
        this.heroUnits = heroUnits;
    }

    public List<EnemyUnit> getEnemyUnits() {
        return enemyUnits;
    }

    public void setEnemyUnits(List<EnemyUnit> enemyUnits) {
        this.enemyUnits = enemyUnits;
    }

    public List<CombatUnit> getAllUnits() {
        return allUnits;
    }

    public void setAllUnits(List<CombatUnit> allUnits) {
        this.allUnits = allUnits;
    }

    public CombatGrid getCombatGrid() {
        return combatGrid;
    }

    public void setCombatGrid(CombatGrid combatGrid) {
        this.combatGrid = combatGrid;
    }

    public int getGpEarned() {
        return gpEarned;
    }

    public void setGpEarned(int gpEarned) {
        this.gpEarned = gpEarned;
    }

    public int getXpEarned() {
        return xpEarned;
    }

    public void setXpEarned(int xpEarned) {
        this.xpEarned = xpEarned;
    }

    public int getApEarned() {
        return apEarned;
    }

    public void setApEarned(int apEarned) {
        this.apEarned = apEarned;
    }

    public String getCombatState() {
        return combatState;
    }

    public void setCombatState(String combatState) {
        this.combatState = combatState;
    }
}
