package com.darksoldier1404.dpr.rplayer;

import org.bukkit.configuration.file.YamlConfiguration;

public class StatValue {
    private final double hpPerStat;
    private final double armorPerStat;
    private final double projectileArmorPerStat;
    private final double damagePerStat;
    private final double projectileDamagePerStat;
    private final double criticalChancePerStat;
    private final double criticalDamagePerStat;
    private final double speedPerStat;
    private final double lifeStealPerStat;
    private final int hpRequireStatPoint;
    private final int armorRequireStatPoint;
    private final int projectileArmorRequireStatPoint;
    private final int damageRequireStatPoint;
    private final int projectileDamageRequireStatPoint;
    private final int criticalChanceRequireStatPoint;
    private final int criticalDamageRequireStatPoint;
    private final int speedRequireStatPoint;
    private final int lifeStealRequireStatPoint;

    public StatValue(YamlConfiguration data) {
        this.hpPerStat = data.getDouble("Stats.hpPerStat");
        this.armorPerStat = data.getDouble("Stats.armorPerStat");
        this.projectileArmorPerStat = data.getDouble("Stats.projectileArmorPerStat");
        this.damagePerStat = data.getDouble("Stats.damagePerStat");
        this.projectileDamagePerStat = data.getDouble("Stats.projectileDamagePerStat");
        this.criticalChancePerStat = data.getDouble("Stats.criticalChancePerStat");
        this.criticalDamagePerStat = data.getDouble("Stats.criticalDamagePerStat");
        this.speedPerStat = data.getDouble("Stats.speedPerStat");
        this.lifeStealPerStat = data.getDouble("Stats.lifeStealPerStat");
        this.hpRequireStatPoint = data.getInt("Stats.hpRequireStatPoint");
        this.armorRequireStatPoint = data.getInt("Stats.armorRequireStatPoint");
        this.projectileArmorRequireStatPoint = data.getInt("Stats.projectileArmorRequireStatPoint");
        this.damageRequireStatPoint = data.getInt("Stats.damageRequireStatPoint");
        this.projectileDamageRequireStatPoint = data.getInt("Stats.projectileDamageRequireStatPoint");
        this.criticalChanceRequireStatPoint = data.getInt("Stats.criticalChanceRequireStatPoint");
        this.criticalDamageRequireStatPoint = data.getInt("Stats.criticalDamageRequireStatPoint");
        this.speedRequireStatPoint = data.getInt("Stats.speedRequireStatPoint");
        this.lifeStealRequireStatPoint = data.getInt("Stats.lifeStealRequireStatPoint");
    }

    public double getHpPerStat() {
        return hpPerStat;
    }

    public double getArmorPerStat() {
        return armorPerStat;
    }

    public double getProjectileArmorPerStat() {
        return projectileArmorPerStat;
    }

    public double getDamagePerStat() {
        return damagePerStat;
    }

    public double getProjectileDamagePerStat() {
        return projectileDamagePerStat;
    }

    public double getCriticalChancePerStat() {
        return criticalChancePerStat;
    }

    public double getCriticalDamagePerStat() {
        return criticalDamagePerStat;
    }

    public double getSpeedPerStat() {
        return speedPerStat;
    }

    public double getLifeStealPerStat() {
        return lifeStealPerStat;
    }

    public int getHpRequireStatPoint() {
        return hpRequireStatPoint;
    }

    public int getArmorRequireStatPoint() {
        return armorRequireStatPoint;
    }

    public int getProjectileArmorRequireStatPoint() {
        return projectileArmorRequireStatPoint;
    }

    public int getDamageRequireStatPoint() {
        return damageRequireStatPoint;
    }

    public int getProjectileDamageRequireStatPoint() {
        return projectileDamageRequireStatPoint;
    }

    public int getCriticalChanceRequireStatPoint() {
        return criticalChanceRequireStatPoint;
    }

    public int getCriticalDamageRequireStatPoint() {
        return criticalDamageRequireStatPoint;
    }

    public int getSpeedRequireStatPoint() {
        return speedRequireStatPoint;
    }

    public int getLifeStealRequireStatPoint() {
        return lifeStealRequireStatPoint;
    }
}
