package com.rpgroyale.combatserver.entities;

import java.util.List;

public class Encounter {

    public Integer encounterId;

    public Integer gameId;

    public Integer zone;

    public Integer xLoc;

    public Integer yLoc;

    public List<Enemy> enemies;
}
