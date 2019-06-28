package com.rpgroyale.combatserver.entities;

import com.rpgroyale.combatserver.utils.PathFindingUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
// enemy and hero combat units will derive from this class
public abstract class CombatUnit
{
    public boolean isHero;
    public GridLocation location;
    public GridLocation movingTo;
    public List<ThreatLevel> threatLevels;
    public AIStrategy aiStrategy;
    public CombatUnit currentTarget;
    public boolean isIncapacitated;
    public boolean isDead;
    public boolean isCasting;
    public boolean isMoving;
    public boolean isAttacking;
    public boolean moveComplete;
    public boolean castingComplete;
    public boolean manualTarget;
    public CombatGrid combatGrid;

    public abstract int getCurrentHp();
    public abstract void adjustHp(int hpChange);
    public abstract void adjustSp(int spChange);
    public abstract void die();
    public abstract void performSkill(CombatSkill skill, List<CombatUnit> targets);
    public abstract int getMinAttackDamage();
    public abstract int getMaxAttackDamage();
    public abstract int getDefenseBlocked();
    public abstract int getResistBlocked();
    public abstract int getAttackRange();
    public abstract String getAttackDamageType();
    public abstract String getUnitName();

    public CombatUnit(int x, int y, boolean isHero, AIStrategy ai) {
        this.isHero = isHero;
        location = new GridLocation(x, y, true, this);
        movingTo = null;
        threatLevels = new ArrayList<>();
        aiStrategy = ai;
        currentTarget = null;
        isIncapacitated = false;
        isDead = false;
        isCasting = false;
        isMoving = false;
        isAttacking = false;
        moveComplete = true;
        castingComplete = true;
        manualTarget = false;
    }

    public void incapacitate()
    {
        isIncapacitated = true;
    }

    public void awake()
    {
        isIncapacitated = false;
    }

    public void moveUnitTo(GridLocation moveLocation)
    {
        if (moveComplete)
        {
            if (!combatGrid.getLocationAt(moveLocation.intX, moveLocation.intY).isOccupied) {
                location = moveLocation;
                GridLocation updatedLocation = combatGrid.getLocationAt(location.intX, location.intY);
                updatedLocation.isOccupied = true;
                updatedLocation.unit = this;
            }
            movingTo = null;
            isMoving = false;
        } else {
            isMoving = true;
            movingTo = moveLocation;
        }
    }

    public void initializeEnemyThreatTargets(List<EnemyUnit> threatTargets) {
        for(CombatUnit unit: threatTargets) {
            ThreatLevel threatLevel = new ThreatLevel();
            threatLevel.unit = unit;
            threatLevel.threatLevel = 0;
            threatLevels.add(threatLevel);
        }
    }

    public void updateEnemyThreatTargets(List<EnemyUnit> threatTargets) {

    }

    public void setCombatGrid(CombatGrid grid) {
        combatGrid = grid;
    }

    public void initializeHeroThreatTargets(List<HeroUnit> threatTargets) {
        for(CombatUnit unit: threatTargets) {
            if (!unit.isDead) {
                ThreatLevel threatLevel = new ThreatLevel();
                threatLevel.unit = unit;
                threatLevel.threatLevel = 0;
                threatLevels.add(threatLevel);
            }
        }
    }

    public void removeUnitFromThreatLevels(CombatUnit unit) {
        ThreatLevel threatLevelToRemove = null;
        for (ThreatLevel threatLevel: threatLevels) {
            if (unit.equals(threatLevel.unit)) {
                threatLevelToRemove = threatLevel;
                break;
            }
        }

        threatLevels.remove(threatLevelToRemove);
    }

    public void performNormalAttack(CombatUnit target)
    {
        Random rand = new Random();
        int damage = rand.nextInt(getMaxAttackDamage() - getMinAttackDamage()) + getMinAttackDamage();
        int blockedDmg = 0;
        if (getAttackDamageType().equals("physical")) {
            blockedDmg = rand.nextInt(target.getDefenseBlocked());
        } else if (getAttackDamageType().equals("magic")) {
            blockedDmg = rand.nextInt(target.getResistBlocked());
        }

        int finalDamage = damage - blockedDmg;
        if (finalDamage < 1) { finalDamage = 1; }
        log.info(getUnitName() + " attacked " + target.getUnitName() + " for " + finalDamage + " damage. " + target.getUnitName() + " HP left: " + (target.getCurrentHp() - finalDamage));
        target.adjustHp(-finalDamage);
    }

    public void runFromUnit(CombatUnit target) {
        GridLocation targetSpot = combatGrid.findPathAway(location.intX, location.intY, target.location.intX, target.location.intY);

        moveUnitTo(targetSpot);
    }

    public CombatUnit chooseTarget()
    {
        if(manualTarget) {
            return currentTarget;
        } else {
            CombatUnit target = null;

            switch (aiStrategy) {
                case HIGHEST_THREAT:
                    target = chooseHighestThreatTarget();
                    break;
                default:
                    target = chooseHighestThreatTarget();
                    break;
            }

            if (target == null) {
                target = chooseClosestOpponentAsTarget();
            }
            currentTarget = target;
            return target;
        }
    }

    public CombatUnit chooseHighestThreatTarget()
    {
        int highestThreat = 0;
        CombatUnit threatTarget = null;
        if (threatLevels == null || threatLevels.size() == 0) {
            return null;
        }

        for(ThreatLevel target : threatLevels)
        {
            if (target.threatLevel > highestThreat)
            {
                highestThreat = target.threatLevel;
                threatTarget = target.unit;
            }
        }
        if(highestThreat == 0) {
            return null;
        }
        return threatTarget;
    }

    public CombatUnit chooseClosestOpponentAsTarget()
    {
        float shortestDistance = 100.0f;
        CombatUnit closestTarget = null;
        for(ThreatLevel target : threatLevels)
        {
            float distance = PathFindingUtil.getDistance(location.intX, location.intY, target.unit.location.intX, target.unit.location.intY);
//            log.info("distance to target " + target.unit.getUnitName() + " is " + distance);
            if (distance < shortestDistance)
            {
                shortestDistance = distance;
                closestTarget = target.unit;
            }
        }
//        if (closestTarget != null) {
//            log.info("closest target set to " + closestTarget.getUnitName());
//        } else {
//            log.info("closest target is null??");
//        }
        return closestTarget;
    }

    public void chaseUnit(CombatUnit target)
    {

        GridLocation moveLocation = combatGrid.findPathTowards(this, target);

//        log.info("moving from " + location.intX + ", " + location.intY + " to " + moveLocation.intX + ", " + moveLocation.intY);
        moveUnitTo(moveLocation);
    }

    // should auto attack if possible after actions (moving, casting)
    public void tryToStartAttacking(CombatUnit target) {
        if (!isMoving && !isCasting && PathFindingUtil.getDistance(location.intX, location.intY, target.location.intX, target.location.intY) <= getAttackRange()) {
            isAttacking = true;
        }
    }

    public int getAttackDamage(CombatUnit defender) {
        Random rand = new Random();
        int attackDamage = rand.nextInt(getMaxAttackDamage() - getMinAttackDamage()) + getMinAttackDamage();
        int defendedDamage = 0;
        if (getAttackDamageType() == "physical") {
            defendedDamage = defender.getDefenseBlocked();
        } else if (getAttackDamageType() == "magic") {
            defendedDamage = defender.getResistBlocked();
        }

        int finalDamage = attackDamage - defendedDamage;
        if (finalDamage < 1) { finalDamage = 1; }
        return finalDamage;
    }

    // to reset unit and allow a new action to take place
    public void resetFlags() {
        isMoving = false;
        isCasting = false;
        isAttacking = false;
    }
}
