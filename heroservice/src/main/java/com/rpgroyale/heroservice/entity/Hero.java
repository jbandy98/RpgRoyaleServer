package com.rpgroyale.heroservice.entity;

import javax.persistence.*;

@Entity
public class Hero {

    @Column(name = "hero_id")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int heroId;

    @Column (name = "game_id")
    private int gameId;

    @Column (name = "player_name")
    private String playerName;

    @Column(name = "hero_name")
    private String heroName;

    @Column
    private int level;

    @Column(name = "class_name")
    private String className;

    @Column
    private int xp;

    @Column
    private int ap;

    @Column(name = "base_strength")
    private int baseStrength;

    @Column(name = "base_dexterity")
    private int baseDexterity;

    @Column(name = "base_speed")
    private int baseSpeed;

    @Column(name = "base_endurance")
    private int baseEndurance;

    @Column(name = "base_spirit")
    private int baseSpirit;

    @Column(name = "base_intelligence")
    private int baseIntelligence;

    @Column (name = "base_willpower")
    private int baseWillpower;

    @Column (name = "base_charisma")
    private int baseCharisma;

    @Column (name = "base_hp")
    private int baseHp;

    @Column (name = "base_sp")
    private int baseSp;

    @Column(name = "bonus_strength")
    private int bonusStrength;

    @Column(name = "bonus_dexterity")
    private int bonusDexterity;

    @Column(name = "bonus_speed")
    private int bonusSpeed;

    @Column (name = "bonus_endurance")
    private int bonusEndurance;
    
    @Column (name = "bonus_spirit")
    private int bonusSpirit;
    
    @Column (name = "bonus_intelligence")
    private int bonusIntelligence;
    
    @Column (name = "bonus_willpower")
    private int bonusWillpower;
    
    @Column (name = "bonus_charisma")
    private int bonusCharisma;
    
    @Column (name = "bonus_hp")
    private int bonusHp;
    
    @Column (name = "bonus_sp")
    private int bonusSp;

    @Column (name = "current_hp")
    private int currentHp;

    @Column (name = "current_sp")
    private int currentSp;

    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getAp() {
        return ap;
    }

    public void setAp(int ap) {
        this.ap = ap;
    }

    public int getBaseStrength() {
        return baseStrength;
    }

    public void setBaseStrength(int baseStrength) {
        this.baseStrength = baseStrength;
    }

    public int getBaseDexterity() {
        return baseDexterity;
    }

    public void setBaseDexterity(int baseDexterity) {
        this.baseDexterity = baseDexterity;
    }

    public int getBaseSpeed() {
        return baseSpeed;
    }

    public void setBaseSpeed(int baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    public int getBaseEndurance() {
        return baseEndurance;
    }

    public void setBaseEndurance(int baseEndurance) {
        this.baseEndurance = baseEndurance;
    }

    public int getBaseSpirit() {
        return baseSpirit;
    }

    public void setBaseSpirit(int baseSpirit) {
        this.baseSpirit = baseSpirit;
    }

    public int getBaseIntelligence() {
        return baseIntelligence;
    }

    public void setBaseIntelligence(int baseIntelligence) {
        this.baseIntelligence = baseIntelligence;
    }

    public int getBaseWillpower() {
        return baseWillpower;
    }

    public void setBaseWillpower(int baseWillpower) {
        this.baseWillpower = baseWillpower;
    }

    public int getBaseCharisma() {
        return baseCharisma;
    }

    public void setBaseCharisma(int baseCharisma) {
        this.baseCharisma = baseCharisma;
    }

    public int getBaseHp() {
        return baseHp;
    }

    public void setBaseHp(int baseHp) {
        this.baseHp = baseHp;
    }

    public int getBaseSp() {
        return baseSp;
    }

    public void setBaseSp(int baseSp) {
        this.baseSp = baseSp;
    }

    public int getBonusStrength() {
        return bonusStrength;
    }

    public void setBonusStrength(int bonusStrength) {
        this.bonusStrength = bonusStrength;
    }

    public int getBonusDexterity() {
        return bonusDexterity;
    }

    public void setBonusDexterity(int bonusDexterity) {
        this.bonusDexterity = bonusDexterity;
    }

    public int getBonusSpeed() {
        return bonusSpeed;
    }

    public void setBonusSpeed(int bonusSpeed) {
        this.bonusSpeed = bonusSpeed;
    }

    public int getBonusEndurance() {
        return bonusEndurance;
    }

    public void setBonusEndurance(int bonusEndurance) {
        this.bonusEndurance = bonusEndurance;
    }

    public int getBonusSpirit() {
        return bonusSpirit;
    }

    public void setBonusSpirit(int bonusSpirit) {
        this.bonusSpirit = bonusSpirit;
    }

    public int getBonusIntelligence() {
        return bonusIntelligence;
    }

    public void setBonusIntelligence(int bonusIntelligence) {
        this.bonusIntelligence = bonusIntelligence;
    }

    public int getBonusWillpower() {
        return bonusWillpower;
    }

    public void setBonusWillpower(int bonusWillpower) {
        this.bonusWillpower = bonusWillpower;
    }

    public int getBonusCharisma() {
        return bonusCharisma;
    }

    public void setBonusCharisma(int bonusCharisma) {
        this.bonusCharisma = bonusCharisma;
    }

    public int getBonusHp() {
        return bonusHp;
    }

    public void setBonusHp(int bonusHp) {
        this.bonusHp = bonusHp;
    }

    public int getBonusSp() {
        return bonusSp;
    }

    public void setBonusSp(int bonusSp) {
        this.bonusSp = bonusSp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    public int getCurrentSp() {
        return currentSp;
    }

    public void setCurrentSp(int currentSp) {
        this.currentSp = currentSp;
    }

}
