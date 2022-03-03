package com.darksoldier1404.dpr.rplayer;

import org.bukkit.configuration.file.YamlConfiguration;

@SuppressWarnings("unused")
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
}
