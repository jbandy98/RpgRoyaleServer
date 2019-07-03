package com.rpgroyale.combatserver.entities;

import com.rpgroyale.combatserver.utils.PathFindingUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CombatGrid {
    private int width = 10;
    private int height = 5;
    private GridLocation[][] location;
    private List<CombatUnit> units;

    public boolean isOccupied(GridLocation checkedLocation)
    {
        return checkedLocation.isOccupied;
    }

    public GridLocation[][] populateMap()
    {
        // first initialize all the cells
        location = new GridLocation[width][height];
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                location[x][y] = new GridLocation(x, y, false, null);
            }
        }

        // 2nd pass, place units in proper locations
        for (CombatUnit unit : units)
        {
            if (unit.isHero)
            {
                location[unit.location.intX][unit.location.intY].isOccupied = true;
                location[unit.location.intX][unit.location.intY].unit = unit;
            }
            else
            {
                if (!unit.isDead)
                {
                    location[unit.location.intX][unit.location.intY].isOccupied = true;
                    location[unit.location.intX][unit.location.intY].unit = unit;
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
        // clean up threat levels
        for(CombatUnit threatUnit: units) {
            threatUnit.removeUnitFromThreatLevels(unit);
        }

        units.remove(unit);

        return units;
    }

    public GridLocation getLocationAt(int x, int y)
    {
        return location[x][y];
    }

    public void addUnitAt(int x, int y, CombatUnit unit)
    {
        location[x][y].unit = unit;
        if (unit != null)
        {
            location[x][y].isOccupied = true;
        }
        else
        {
            location[x][y].isOccupied = false;
        }
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
