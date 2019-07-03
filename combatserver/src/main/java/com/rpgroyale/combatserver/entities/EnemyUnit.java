package com.rpgroyale.combatserver.entities;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class EnemyUnit extends CombatUnit {
    public Enemy enemy;

    public EnemyUnit(int x, int y, boolean isHero, AIStrategy ai) {
        super(x,y,isHero,ai);
    }

    @Override
    public String getUnitName() {
        return enemy.enemyName;
    }

    @Override
    public int getGpValue() {
        return enemy.gpValue;
    }

    @Override
    public int getXpValue() {
        return enemy.xpValue;
    }

    @Override
    public int getApValue() {
        return enemy.getApValue();
    }

    @Override
    public void adjustHp(int hpChange)
    {
        enemy.currentHp += hpChange;
        if (enemy.currentHp > enemy.hp)
        {
            enemy.currentHp = enemy.hp;
        }
        if (enemy.currentHp <= 0)
        {
            enemy.currentHp = 0;
            isIncapacitated = true;
            die();
        }
    }

    @Override
    public void adjustSp(int spChange)
    {
        enemy.currentSp += spChange;
        if (enemy.currentSp > enemy.sp)
        {
            enemy.currentSp = enemy.sp;
        }
        if (enemy.currentSp < 0)
        {
            enemy.currentSp = 0;
        }
    }

    @Override
    public String getAttackDamageType() {
        return enemy.attackType;
    }

    @Override
    public void performSkill(CombatSkill skill, List<com.rpgroyale.combatserver.entities.CombatUnit> targets) {
        // todo: implement skills
    }

    @Override
    public int getMinAttackDamage() {
        return enemy.getMinAutoDamage();
    }

    @Override
    public int getMaxAttackDamage() {
        return enemy.getMaxAutoDamage();
    }

    @Override
    public int getDefenseBlocked() {
        return enemy.defense();
    }

    @Override
    public int getResistBlocked() {
        return enemy.resist();
    }

    @Override
    public int getAttackRange() {
        return enemy.attackRange;
    }

    @Override
    public void die()
    {
        isIncapacitated = true;
        isDead = true;
        log.info(getUnitName() + " has died!");
    }

    @Override
    public int getCurrentHp() {
        return enemy.getCurrentHp();
    }

}
