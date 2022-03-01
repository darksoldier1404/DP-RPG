package com.darksoldier1404.dpr.rplayer;

import org.bukkit.configuration.file.YamlConfiguration;

@SuppressWarnings("unused")
public class Stats {
    private int point;
    private int hp;
    private int armor;
    private int projectileArmor;
    private int damage;
    private int projectileDamage;
    private int criticalChance;
    private int criticalDamage;
    private int speed;
    private int lifeSteal;
    private final int maxHp;
    private final int maxArmor;
    private final int maxProjectileArmor;
    private final int maxDamage;
    private final int maxProjectileDamage;
    private final int maxCriticalChance;
    private final int maxCriticalDamage;
    private final int maxSpeed;
    private final int maxLifeSteal;

    public Stats(YamlConfiguration data) {
        this.maxHp = data.getInt("Stats.MaxHp");
        this.maxArmor = data.getInt("Stats.MaxArmor");
        this.maxProjectileArmor = data.getInt("Stats.MaxProjectileArmor");
        this.maxDamage = data.getInt("Stats.MaxDamage");
        this.maxProjectileDamage = data.getInt("Stats.MaxProjectileDamage");
        this.maxCriticalChance = data.getInt("Stats.MaxCriticalChance");
        this.maxCriticalDamage = data.getInt("Stats.MaxCriticalDamage");
        this.maxSpeed = data.getInt("Stats.MaxSpeed");
        this.maxLifeSteal = data.getInt("Stats.MaxLifeSteal");
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getProjectileArmor() {
        return projectileArmor;
    }

    public void setProjectileArmor(int projectileArmor) {
        this.projectileArmor = projectileArmor;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getProjectileDamage() {
        return projectileDamage;
    }

    public void setProjectileDamage(int projectileDamage) {
        this.projectileDamage = projectileDamage;
    }

    public int getCriticalChance() {
        return criticalChance;
    }

    public void setCriticalChance(int criticalChance) {
        this.criticalChance = criticalChance;
    }

    public int getCriticalDamage() {
        return criticalDamage;
    }

    public void setCriticalDamage(int criticalDamage) {
        this.criticalDamage = criticalDamage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getLifeSteal() {
        return lifeSteal;
    }

    public void setLifeSteal(int lifeSteal) {
        this.lifeSteal = lifeSteal;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getMaxArmor() {
        return maxArmor;
    }

    public int getMaxProjectileArmor() {
        return maxProjectileArmor;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public int getMaxProjectileDamage() {
        return maxProjectileDamage;
    }

    public int getMaxCriticalChance() {
        return maxCriticalChance;
    }

    public int getMaxCriticalDamage() {
        return maxCriticalDamage;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public int getMaxLifeSteal() {
        return maxLifeSteal;
    }
}
