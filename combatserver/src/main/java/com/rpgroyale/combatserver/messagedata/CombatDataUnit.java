package com.rpgroyale.combatserver.messagedata;

import com.rpgroyale.combatserver.entities.CombatUnit;

public class CombatDataUnit {
    public int unitId;
    public String unitName;
    public int level;
    public boolean isHero;
    public int locX;
    public int locY;
    public int targetX;
    public int targetY;
    public int hp;
    public int maxHp;
    public int sp;
    public int maxSp;
    public String aiStrategy;
    public int currentTarget;
    public boolean isIncapacitated;
    public boolean isDead;
    public boolean isCasting;
    public boolean isMoving;
    public boolean moveComplete;
    public boolean isAttacking;
    public boolean castingComplete;

    public CombatDataUnit(CombatUnit unit) {
        this.unitId = unit.getUnitId();
        this.unitName = unit.getUnitName();
        this.level = unit.getLevel();
        this.isHero = unit.isHero();
        this.locX = unit.getLocX();
        this.locY = unit.getLocY();
        this.targetX = unit.getTargetX();
        this.targetY = unit.getTargetY();
        this.hp = unit.getCurrentHp();
        this.maxHp = unit.getMaxHp();
        this.sp = unit.getCurrentSp();
        this.maxSp = unit.getMaxSp();
        this.aiStrategy = unit.getAiStrategy();
        if (unit.getCurrentTarget() == null) {
            this.currentTarget = -1;
        } else {
            this.currentTarget = unit.getCurrentTarget();
        }
        this.isIncapacitated = unit.isIncapacitated();
        this.isDead = unit.isDead();
        this.isCasting = unit.isCasting();
        this.isMoving = unit.isMoving();
        this.moveComplete = unit.moveComplete;
        this.isAttacking = unit.isAttacking();
        this.castingComplete = unit.castingComplete;
    }
}
