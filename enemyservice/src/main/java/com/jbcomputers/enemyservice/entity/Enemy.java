package com.jbcomputers.enemyservice.entity;

import javax.persistence.*;

@Entity
@Table(name="enemy")
public class Enemy {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Integer encEnemyId;

    @Column(name="enemy_id")
    public Integer enemyId;

    @Column(name="encounter_id")
    public int encounterId;

    @Column(name="game_id")
    public int gameId;

    @Column(name="enemy_name")
    public String enemyName;

    @Column()
    public int level;

    @Column(name="xp_value")
    public int xpValue;

    @Column(name="gp_value")
    public int gpValue;

    @Column(name="ap_value")
    public int apValue;

    @Column()
    public int strength;

    @Column()
    public int dexterity;

    @Column()
    public int speed;

    @Column()
    public int endurance;

    @Column()
    public int spirit;

    @Column()
    public int intelligence;

    @Column()
    public int willpower;

    @Column()
    public int charisma;

    @Column()
    public int hp;

    @Column()
    public int sp;

    @Column(name="current_hp")
    public int currentHp;

    @Column(name="current_sp")
    public int currentSp;

    @Column(name = "attack_range")
    public int attackRange;

    @Column(name = "attack_type")
    public String attackType;

    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public String getAttackType() {
        return attackType;
    }

    public void setAttackType(String attackType) {
        this.attackType = attackType;
    }

    public int getEnemyId() {
        return enemyId;
    }

    public void setEnemyId(int enemyId) {
        this.enemyId = enemyId;
    }

    public int getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(int encounterId) {
        this.encounterId = encounterId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXpValue() {
        return xpValue;
    }

    public void setXpValue(int xpValue) {
        this.xpValue = xpValue;
    }

    public int getGpValue() {
        return gpValue;
    }

    public void setGpValue(int gpValue) {
        this.gpValue = gpValue;
    }

    public int getApValue() {
        return apValue;
    }

    public void setApValue(int apValue) {
        this.apValue = apValue;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public int getSpirit() {
        return spirit;
    }

    public void setSpirit(int spirit) {
        this.spirit = spirit;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWillpower() {
        return willpower;
    }

    public void setWillpower(int willpower) {
        this.willpower = willpower;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getSp() {
        return sp;
    }

    public void setSp(int sp) {
        this.sp = sp;
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

    public float attackSpeed()
    {
        return 1 + ((speed / 2) / 100);
    }

    public int minAutoDamage()
    {
        return strength * 1;
    }

    public int maxAutoDamage()
    {
        return strength * 2;
    }

    public int armorRating()
    {
        return 0;
    }

    public int resistRating()
    {
        return 0;
    }

    public int defense()
    {
        return armorRating() + dexterity;
    }

    public float blockChance()
    {
        return dexterity * .005f;
    }

    public int block()
    {
        return strength;
    }

    public int resist()
    {
        return resistRating() + willpower;
    }

    public float dodgeChance()
    {
        return 0.05f + speed * .005f;
    }

    public float parryChance()
    {
        return 0.05f + speed * 0.005f;
    }

    public float castingSpeed()
    {
        return 1 + speed * 0.01f;
    }

    public float hitChance()
    {
        return 0.8f + dexterity * 0.005f;
    }

    public float critChance()
    {
        return 0.05f + dexterity * 0.005f;
    }

    public float magicDeflectChance()
    {
        return 0.05f + willpower * 0.005f;
    }

    public float magicAbsorbChance()
    {
        return 0.05f + willpower * 0.005f;
    }

    public float leadershipBonus()
    {
        return 1 + charisma * 0.01f;
    }

    public float regenHpSpeed()
    {
        return 0.01f + endurance * 0.001f;
    }

    public float regenSpSpeed()
    {
        return 0.01f + spirit * 0.001f;
    }
}

