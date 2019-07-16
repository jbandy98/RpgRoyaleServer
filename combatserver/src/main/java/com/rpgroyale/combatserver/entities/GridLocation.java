package com.rpgroyale.combatserver.entities;

public class GridLocation {
    public int intX;
    public int intY;
    public boolean isOccupied;
    public Integer unitId;

    public GridLocation(int x, int y, boolean occupied, Integer unitId)
    {
        this.intX = x;
        this.intY = y;
        this.isOccupied = occupied;
        this.unitId = unitId;
    }
}
