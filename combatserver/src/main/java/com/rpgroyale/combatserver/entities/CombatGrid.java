package com.rpgroyale.combatserver.entities;

import com.rpgroyale.combatserver.utils.PathFindingUtil;

import java.util.ArrayList;
import java.util.List;

public class CombatGrid {
    private int width = 10;
    private int height = 5;
    private GridLocation[][] location;
    private List<CombatUnit> units;

    public CombatGrid() {
        units = new ArrayList<>();
        location = new GridLocation[width][height];
    }
    public boolean isOccupied(GridLocation checkedLocation)
    {
        return checkedLocation.isOccupied;
    }

    public GridLocation[][] populateMap()
    {
        // first initialize all the cells
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                location[x][y] = new GridLocation(x, y, false, -1);
            }
        }

        // 2nd pass, place units in proper locations
        for (CombatUnit unit : units)
        {
            if (unit.isHero)
            {
                location[unit.locX][unit.locY].isOccupied = true;
                location[unit.locX][unit.locY].unitId = unit.getUnitId();
            }
            else
            {
                if (!unit.isDead)
                {
                    location[unit.locX][unit.locY].isOccupied = true;
                    location[unit.locX][unit.locY].unitId = unit.getUnitId();
                }
            }
        }

        return location;
    }

    public void setCombatGridUnits(List<CombatUnit> units)
    {
        this.units = units;
    }

    public List<CombatUnit> getCombatGridUnits()
    {
        return this.units;
    }

    public List<CombatUnit> addUnitToCombatGrid(CombatUnit unit)
    {
        units.add(unit);

        return units;
    }

    public List<CombatUnit> removeUnitFromCombatGrid(CombatUnit unit)
    {
        units.remove(unit);

        return units;
    }

    public GridLocation getLocationAt(int x, int y)
    {
        return location[x][y];
    }

    public void addUnitAt(int x, int y, Integer unitId)
    {
        location[x][y].unitId = unitId;
        if (unitId != -1)
        {
            location[x][y].isOccupied = true;
        }
        else
        {
            location[x][y].isOccupied = false;
        }
    }

    public void moveUnitTo(CombatUnit unit, int targetX, int targetY)
    {
        if (unit.moveComplete)
        {
            if (!getLocationAt(targetX, targetY).isOccupied) {
                unit.locX = targetX;
                unit.locY = targetY;
                GridLocation updatedLocation = getLocationAt(unit.locX, unit.locY);
                updatedLocation.isOccupied = true;
                updatedLocation.unitId = unit.getUnitId();
            }
            unit.targetX = -1;
            unit.targetY = -1;
            unit.isMoving = false;
        } else {
            unit.isMoving = true;
            unit.targetX = targetX;
            unit.targetY = targetY;
        }
    }

    public void runFromUnit(CombatUnit unit, CombatUnit target) {
        GridLocation targetSpot = PathFindingUtil.instance.findPathAway(unit.locX, unit.locY, target.locX, target.locY);
        moveUnitTo(unit, targetSpot.intX, targetSpot.intY);
    }

    public void chaseUnit(CombatUnit unit, CombatUnit target)
    {
        GridLocation moveLocation = PathFindingUtil.instance.findPathTowards(unit, target);

        if (moveLocation != null)
            moveUnitTo(unit, moveLocation.intX, moveLocation.intY);
    }

    public Integer chooseClosestOpponentAsTarget(boolean isHero, int x, int y, List<CombatUnit> units)
    {
        float shortestDistance = 100.0f;
        Integer closestTarget = -1;
        for(CombatUnit target : units)
        {
            if (target.isHero != isHero) {
                float distance = PathFindingUtil.getDistance(x, y, target.locX, target.locY);
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    closestTarget = target.unitId;
                }
            }
        }
        return closestTarget;
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

    public GridLocation[][] getLocation() {
        return location;
    }

    public void setLocation(GridLocation[][] location) {
        this.location = location;
    }

    public List<CombatUnit> getUnits() {
        return units;
    }

    public void setUnits(List<CombatUnit> units) {
        this.units = units;
    }
}
