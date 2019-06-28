package com.rpgroyale.gameserver.components;

public class Enemy {

    public Integer encEnemyId;

    public Integer enemyId;

    public int encounterId;

    public int gameId;

    public String enemyName;

    public int level;

    public int xpValue;

    public int gpValue;

    public int apValue;

    public int strength;

    public int dexterity;

    public int speed;

    public int endurance;

    public int spirit;

    public int intelligence;

    public int willpower;

    public int charisma;

    public int hp;

    public int sp;

    public int currentHp;

    public int currentSp;

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

