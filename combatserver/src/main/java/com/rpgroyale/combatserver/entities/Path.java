package com.rpgroyale.combatserver.entities;

import java.util.ArrayList;
import java.util.List;

public class Path {
    public List<GridLocation> steps;

    public Path() {
        steps = new ArrayList<>();
    }

    public void appendStep(GridLocation location)
    {
        steps.add(location);
    }

    public void prependStep(GridLocation location)
    {
        steps.add(0, location);
    }
}
