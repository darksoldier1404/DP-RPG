package com.darksoldier1404.dpr.rplayer;

import com.darksoldier1404.dpr.enums.IllustrateType;
import com.darksoldier1404.dpr.illustrate.Illustrate;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
public class RPlayer {
    private final Player p;
    private int level;
    private double exp;
    private final Stats stat;
    private final YamlConfiguration data;

    private final Map<String, Illustrate> illustrate = new HashMap<>();

    public RPlayer(Player p, YamlConfiguration data, YamlConfiguration config) {
        this.p = p;
        this.data = data;
        stat = new Stats(config);
        level = data.getInt("RPlayer.LV");
        exp = data.getDouble("RPlayer.EXP");
        initStats(data);
        initIllustrate(data);
    }

    public void initStats(YamlConfiguration data) {
        stat.setPoint(data.getInt("Stats.point"));
        stat.setHp(data.getInt("Stats.hp"));
        stat.setArmor(data.getInt("Stats.armor"));
        stat.setProjectileArmor(data.getInt("Stats.projectileArmor"));
        stat.setDamage(data.getInt("Stats.damage"));
        stat.setProjectileDamage(data.getInt("Stats.projectileDamage"));
        stat.setCriticalChance(data.getInt("Stats.criticalChance"));
        stat.setCriticalDamage(data.getInt("Stats.criticalDamage"));
        stat.setSpeed(data.getInt("Stats.speed"));
        stat.setLifeSteal(data.getInt("Stats.lifeSteal"));
    }

    public void initIllustrate(YamlConfiguration data) {
        if (data.isSet("Illustrate")) {
            for (String key : data.getConfigurationSection("Illustrate").getKeys(false)) {
                Illustrate ill = new Illustrate(key);
                for (String key2 : data.getConfigurationSection("Illustrate." + key).getKeys(false)) {
                    if(key2.equals("isRecived")) {
                        ill.setRecivedPrize(data.getBoolean("Illustrate." + key + "." + key2));
                    }else{
                        ill.addSeal(IllustrateType.valueOf(key2));
                    }
                }
                illustrate.put(key, ill);
            }
        }
    }

    public void saveData() {
        data.set("RPlayer.LV", level);
        data.set("RPlayer.EXP", exp);
        data.set("Stats.point", stat.getPoint());
        data.set("Stats.hp", stat.getHp());
        data.set("Stats.armor", stat.getArmor());
        data.set("Stats.projectileArmor", stat.getProjectileArmor());
        data.set("Stats.damage", stat.getDamage());
        data.set("Stats.projectileDamage", stat.getProjectileDamage());
        data.set("Stats.criticalChance", stat.getCriticalChance());
        data.set("Stats.criticalDamage", stat.getCriticalDamage());
        data.set("Stats.speed", stat.getSpeed());
        data.set("Stats.lifeSteal", stat.getLifeSteal());
        illustrate.forEach((key, value) -> value.getSealType().forEach(sealType -> data.set("Illustrate." + key + "." + sealType.name(), true)));
        illustrate.forEach((key, value) -> data.set("Illustrate." + key + ".isRecived", value.isRecivedPrize()));
    }

    public Player getPlayer() {
        return p;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getExp() {
        return exp;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public Stats getStat() {
        return stat;
    }

    public Map<String, Illustrate> getIllustrate() {
        return illustrate;
    }

    public YamlConfiguration getData() {
        return data;
    }
}
