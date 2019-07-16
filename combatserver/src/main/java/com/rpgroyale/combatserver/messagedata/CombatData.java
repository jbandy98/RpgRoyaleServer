package com.rpgroyale.combatserver.messagedata;


import java.util.List;

public class CombatData {
    public String playerId;
    public Integer gameId;
    public Integer encounterId;
    public String gameState;
    public List<CombatDataUnit> dataUnits;

    public CombatData(String playerId, Integer gameId, Integer encounterId, String gameState, List<CombatDataUnit> dataUnits) {
        this.playerId = playerId;
        this.gameId = gameId;
        this.encounterId = encounterId;
        this.gameState = gameState;
        this.dataUnits = dataUnits;
    }

    public String getPlayerId() {
        return playerId;
    }

}
