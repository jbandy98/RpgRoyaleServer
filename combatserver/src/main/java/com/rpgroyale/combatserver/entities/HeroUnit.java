package com.rpgroyale.combatserver.entities;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class HeroUnit extends CombatUnit {
    public Hero hero;


    public HeroUnit(int x, int y, boolean isHero, AIStrategy ai) {
        super(x,y,isHero,ai);
    }

    @Override
    public String getUnitName() {
        return hero.getHeroName();
    }

    @Override
    public void adjustHp(int hpChange) {
        hero.setCurrentHp(hero.getCurrentHp() + hpChange);
        if (hero.getCurrentHp() > hero.getMaxHp()) {
            hero.setCurrentHp(hero.getMaxHp());
        }

        if (hero.getCurrentHp() <= 0) {
            hero.setCurrentHp(0);
            isIncapacitated = true;
            die();
        }
    }

    @Override
    public void adjustSp(int spChange) {
        hero.setCurrentSp(hero.getCurrentSp() + spChange);
        if(hero.getCurrentSp() > hero.getMaxSp()) {
            hero.setCurrentSp(hero.getMaxSp());
        }
        if(hero.getCurrentSp() < 0) {
            hero.setCurrentSp(0);
        }
    }

    @Override
    public String getAttackDamageType() {
        return hero.getAttackType();
    }

    @Override
    public void performSkill(CombatSkill skill, List<CombatUnit> targets) {
        // todo: implement skills
    }

    @Override
    public int getMinAttackDamage() {
        return hero.getMinAutoDamage();
    }

    @Override
    public int getMaxAttackDamage() {
        return hero.getMaxAutoDamage();
    }

    @Override
    public int getDefenseBlocked() {
        return hero.getDefense();
    }

    @Override
    public int getResistBlocked() {
        return hero.getResist();
    }

    @Override
    public int getAttackRange() {
        return hero.getAttackRange();
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
        return hero.getCurrentHp();
    }

}
