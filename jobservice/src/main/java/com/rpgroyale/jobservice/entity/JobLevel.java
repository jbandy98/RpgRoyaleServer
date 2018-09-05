package com.rpgroyale.jobservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class JobLevel {

    @Id
    private long id;

    @Column (name="job_name")
    private String jobName;

    @Column
    private int level;

    @Column (name="xp_to_level")
    private int xpToLevel;

    // all of the remaining values are their new values when the level changes. this is total, not cumulative

    @Column (name = "base_strength")
    private int baseStrength;

    @Column (name = "base_dexterity")
    private int baseDexterity;

    @Column (name = "base_speed")
    private int baseSpeed;

    @Column (name = "base_endurance")
    private int baseEndurance;

    @Column (name = "base_spirit")
    private int baseSpirit;

    @Column (name = "base_intelligence")
    private int baseIntelligence;

    @Column (name = "base_willpower")
    private int baseWillpower;

    @Column (name = "base_charisma")
    private int baseCharisma;

    @Column (name = "base_hp")
    private int baseHp;

    @Column (name = "base_sp")
    private int baseSp;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXpToLevel() {
        return xpToLevel;
    }

    public void setXpToLevel(int xpToLevel) {
        this.xpToLevel = xpToLevel;
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
}
