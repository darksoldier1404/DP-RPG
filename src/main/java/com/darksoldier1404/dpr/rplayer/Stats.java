package com.darksoldier1404.dpr.rplayer;

import com.darksoldier1404.dpr.DRPG;
import com.darksoldier1404.dpr.enums.StatsType;
import com.darksoldier1404.dpr.events.obj.StatLevelUPEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

@SuppressWarnings("all")
public class Stats {
    private final StatValue sv;
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
        sv = DRPG.getInstance().statValue;
    }

    public void init(Player p) {
        StatValue sv = DRPG.getInstance().statValue;
        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue() + (hp * sv.getHpPerStat()));
        p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getDefaultValue() + (sv.getSpeedPerStat() * speed));
    }

    public void playSound(Player p) {
        p.getWorld().playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
    }

    public void addStat(StatsType type, Player p) {
        Bukkit.getServer().getPluginManager().callEvent(new StatLevelUPEvent(p, this, type));
        switch (type) {
            case HP:
                if (point >= sv.getHpPerStat()) {
                    hp++;
                    point -= sv.getHpPerStat();
                    playSound(p);
                    return;
                }
            case ARMOR:
                if (point >= sv.getArmorPerStat()) {
                    armor++;
                    point -= sv.getArmorPerStat();
                    playSound(p);
                    return;
                }
            case PROJECTILE_ARMOR:
                if (point >= sv.getProjectileArmorPerStat()) {
                    projectileArmor++;
                    point -= sv.getProjectileArmorPerStat();
                    playSound(p);
                    return;
                }
            case DAMAGE:
                if (point >= sv.getDamagePerStat()) {
                    damage++;
                    point -= sv.getDamagePerStat();
                    playSound(p);
                    return;
                }
            case PROJECTILE_DAMAGE:
                if (point >= sv.getProjectileDamagePerStat()) {
                    projectileDamage++;
                    point -= sv.getProjectileDamagePerStat();
                    playSound(p);
                    return;
                }
            case CRITICAL_CHANCE:
                if (point >= sv.getCriticalChancePerStat()) {
                    criticalChance++;
                    point -= sv.getCriticalChancePerStat();
                    playSound(p);
                    return;
                }
            case CRITICAL_DAMAGE:
                if (point >= sv.getCriticalDamagePerStat()) {
                    criticalDamage++;
                    point -= sv.getCriticalDamagePerStat();
                    playSound(p);
                    return;
                }
            case SPEED:
                if (point >= sv.getSpeedPerStat()) {
                    speed++;
                    point -= sv.getSpeedPerStat();
                    playSound(p);
                    return;
                }
            case LIFESTEAL:
                if (point >= sv.getLifeStealPerStat()) {
                    lifeSteal++;
                    point -= sv.getLifeStealPerStat();
                    playSound(p);
                    return;
                }
        }
        p.sendMessage(DRPG.getInstance().prefix + "스텟 포인트가 부족합니다.");
    }

    public int getStat(StatsType type) {
        switch (type) {
            case HP: return hp;
            case ARMOR : return armor;
            case PROJECTILE_ARMOR : return projectileArmor;
            case DAMAGE : return damage;
            case PROJECTILE_DAMAGE : return projectileDamage;
            case CRITICAL_CHANCE : return criticalChance;
            case CRITICAL_DAMAGE : return criticalDamage;
            case SPEED : return speed;
            case LIFESTEAL : return lifeSteal;
            case POINT : return point;
        }
        return 0;
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

