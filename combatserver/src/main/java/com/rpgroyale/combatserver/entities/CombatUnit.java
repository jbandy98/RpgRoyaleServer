package com.rpgroyale.combatserver.entities;

import com.rpgroyale.combatserver.utils.PathFindingUtil;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
// enemy and hero combat units will derive from this class
@Entity
@Table(name="combat_unit")
public abstract class CombatUnit
{

    @Id
    @Column(name="unit_id")
    Integer unitId;

    @Column(name="is_hero")
    boolean isHero;

    @Column(name="loc_x")
    int locX;

    @Column(name="loc_y")
    int locY;

    @Column(name="target_x")
    int targetX;

    @Column(name="target_y")
    int targetY;

    @Column(name="ai_strategy")
    public String aiStrategy;

    @Column(name="current_target")
    public Integer currentTarget;

    @Column(name="incapacitated")
    public boolean isIncapacitated;

    @Column(name="dead")
    public boolean isDead;

    @Column(name="casting")
    public boolean isCasting;

    @Column(name="moving")
    public boolean isMoving;

    @Column(name="attacking")
    public boolean isAttacking;

    @Column(name="move_complete")
    public boolean moveComplete;

    @Column(name="casting_complete")
    public boolean castingComplete;

    @Column(name="manual_target")
    public boolean manualTarget;

    @ElementCollection
    public List<ThreatLevel> threatLevels;

    public abstract int getCurrentHp();
    public abstract int getMaxHp();
    public abstract int getCurrentSp();
    public abstract int getMaxSp();
    public abstract int getLevel();
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
    public abstract int getGpValue();
    public abstract int getXpValue();
    public abstract int getApValue();

    public CombatUnit(int x, int y, boolean isHero, String ai) {
        this.isHero = isHero;
        locX = x;
        locY = y;
        targetX = -1;
        targetY = -1;
        aiStrategy = ai;
        currentTarget = -1;
        isIncapacitated = false;
        isDead = false;
        isCasting = false;
        isMoving = false;
        isAttacking = false;
        moveComplete = true;
        castingComplete = true;
        manualTarget = false;
        threatLevels = new ArrayList<>();
    }

    public void incapacitate()
    {
        isIncapacitated = true;
    }

    public void awake()
    {
        isIncapacitated = false;
    }

    public void initializeThreatTargets(List<CombatUnit> threatTargets) {
        for(CombatUnit unit: threatTargets) {
            if (unit.isHero() != isHero) {
                ThreatLevel threatLevel = new ThreatLevel();
                threatLevel.setUnitIndex(unit.getUnitId());
                threatLevel.setThreatLevel(0);
                threatLevels.add(threatLevel);
            }
        }
    }

    public void removeUnitFromThreatLevels(Integer unitId) {
        ThreatLevel threatLevelToRemove = null;
        for (ThreatLevel threatLevel: threatLevels) {
            if (unitId == threatLevel.getUnitIndex()) {
                threatLevelToRemove = threatLevel;
                break;
            }
        }
        if (threatLevelToRemove != null)
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

    public Integer chooseTarget()
    {
        if(manualTarget) {
            return currentTarget;
        } else {
            Integer target = -1;

            switch (aiStrategy) {
                case "HIGHEST_THREAT":
                    target = chooseHighestThreatTarget();
                    break;
                default:
                    target = chooseHighestThreatTarget();
                    break;
            }

            currentTarget = target;
            return target;
        }
    }

    public Integer chooseHighestThreatTarget()
    {
        int highestThreat = 0;
        Integer threatTarget = -1;
        if (threatLevels == null || threatLevels.size() == 0) {
            return -1;
        }

        for(ThreatLevel target : threatLevels)
        {
            if (target.getThreatLevel() > highestThreat)
            {
                highestThreat = target.getThreatLevel();
                threatTarget = target.getUnitIndex();
            }
        }
        if(highestThreat == 0) {
            return -1;
        }
        return threatTarget;
    }

//
//
//    // should auto attack if possible after actions (moving, casting)
//    public void tryToStartAttacking(CombatUnit target) {

//    }

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

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public boolean isHero() {
        return isHero;
    }

    public void setHero(boolean hero) {
        isHero = hero;
    }

    public int getLocX() {
        return locX;
    }

    public void setLocX(int locX) {
        this.locX = locX;
    }

    public int getLocY() {
        return locY;
    }

    public void setLocY(int locY) {
        this.locY = locY;
    }

    public int getTargetX() {
        return targetX;
    }

    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }

    public String getAiStrategy() {
        return aiStrategy;
    }

    public void setAiStrategy(String aiStrategy) {
        this.aiStrategy = aiStrategy;
    }

    public Integer getCurrentTarget() {
        return currentTarget;
    }

    public void setCurrentTarget(Integer currentTarget) {
        this.currentTarget = currentTarget;
    }

    public boolean isIncapacitated() {
        return isIncapacitated;
    }

    public void setIncapacitated(boolean incapacitated) {
        isIncapacitated = incapacitated;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isCasting() {
        return isCasting;
    }

    public void setCasting(boolean casting) {
        isCasting = casting;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }

    public boolean isMoveComplete() {
        return moveComplete;
    }

    public void setMoveComplete(boolean moveComplete) {
        this.moveComplete = moveComplete;
    }

    public boolean isCastingComplete() {
        return castingComplete;
    }

    public void setCastingComplete(boolean castingComplete) {
        this.castingComplete = castingComplete;
    }

    public boolean isManualTarget() {
        return manualTarget;
    }

    public void setManualTarget(boolean manualTarget) {
        this.manualTarget = manualTarget;
    }

    public List<ThreatLevel> getThreatLevels() {
        return threatLevels;
    }

    public void setThreatLevels(List<ThreatLevel> threatLevels) {
        this.threatLevels = threatLevels;
    }

}
