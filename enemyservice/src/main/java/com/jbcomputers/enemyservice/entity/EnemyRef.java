package com.jbcomputers.enemyservice.entity;

import javax.persistence.*;

@Entity
@Table(name="enemy_ref")
public class EnemyRef {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="enemy_id")
    public Integer enemyId;

    @Column(name="enemy_name")
    public String enemyName;

    @Column()
    public Integer level;

    @Column(name="xp_value")
    public Integer xpValue;

    @Column(name="gp_value")
    public Integer gpValue;

    @Column(name="ap_value")
    public Integer apValue;

    @Column()
    public Integer strength;

    @Column()
    public Integer dexterity;

    @Column()
    public Integer speed;

    @Column()
    public Integer endurance;

    @Column()
    public Integer spirit;

    @Column()
    public Integer intelligence;

    @Column()
    public Integer willpower;

    @Column()
    public Integer charisma;

    @Column()
    public Integer hp;

    @Column()
    public Integer sp;

    @Column(name="attack_range")
    private int attackRange;

    @Column(name="attack_type")
    private String attackType;

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

    public Integer getEnemyId() {
        return enemyId;
    }

    public void setEnemyId(Integer enemyId) {
        this.enemyId = enemyId;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getXpValue() {
        return xpValue;
    }

    public void setXpValue(Integer xpValue) {
        this.xpValue = xpValue;
    }

    public Integer getGpValue() {
        return gpValue;
    }

    public void setGpValue(Integer gpValue) {
        this.gpValue = gpValue;
    }

    public Integer getApValue() {
        return apValue;
    }

    public void setApValue(Integer apValue) {
        this.apValue = apValue;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Integer getDexterity() {
        return dexterity;
    }

    public void setDexterity(Integer dexterity) {
        this.dexterity = dexterity;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getEndurance() {
        return endurance;
    }

    public void setEndurance(Integer endurance) {
        this.endurance = endurance;
    }

    public Integer getSpirit() {
        return spirit;
    }

    public void setSpirit(Integer spirit) {
        this.spirit = spirit;
    }

    public Integer getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(Integer intelligence) {
        this.intelligence = intelligence;
    }

    public Integer getWillpower() {
        return willpower;
    }

    public void setWillpower(Integer willpower) {
        this.willpower = willpower;
    }

    public Integer getCharisma() {
        return charisma;
    }

    public void setCharisma(Integer charisma) {
        this.charisma = charisma;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public Integer getSp() {
        return sp;
    }

    public void setSp(Integer sp) {
        this.sp = sp;
    }

    public float attackSpeed()
    {
        return 1 + ((speed / 2) / 100);
    }

    public Integer minAutoDamage()
    {
        return strength * 1;
    }

    public Integer maxAutoDamage()
    {
        return strength * 2;
    }

    public Integer armorRating()
    {
        return 0;
    }

    public Integer resistRating()
    {
        return 0;
    }

    public Integer defense()
    {
        return armorRating() + dexterity;
    }

    public float blockChance()
    {
        return dexterity * .005f;
    }

    public Integer block()
    {
        return strength;
    }

    public Integer resist()
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
