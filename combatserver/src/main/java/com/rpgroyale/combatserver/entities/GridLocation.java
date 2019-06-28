package com.rpgroyale.combatserver.entities;

public class GridLocation {
    public int intX;
    public int intY;
    public boolean isOccupied;
    public CombatUnit unit;

    public GridLocation(int x, int y, boolean occupied, CombatUnit unit)
    {
        this.intX = x;
        this.intY = y;
        this.isOccupied = occupied;
        this.unit = null;
    }
}
