package com.darksoldier1404.dpr.functions;

import com.darksoldier1404.dppc.utils.ConfigUtils;
import com.darksoldier1404.dpr.DRPG;
import com.darksoldier1404.dpr.events.obj.RPlayerExpGainEvent;
import com.darksoldier1404.dpr.events.obj.RPlayerLevelUPEvent;
import com.darksoldier1404.dpr.rplayer.RPlayer;
import io.lumine.xikage.mythicmobs.MythicMobs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.util.Map;
import java.util.UUID;

public class DRAUFunction {
    private static final DRPG plugin = DRPG.getInstance();
    private static final YamlConfiguration config = plugin.config;
    private static final YamlConfiguration levels = plugin.levels;
    private static final String prefix = plugin.prefix;
    private static final Map<UUID, RPlayer> rplayers = plugin.rplayers;
    private static final PluginManager pm = Bukkit.getPluginManager();

    public static void addLevel(Player p, int level) {
        RPlayer rp = rplayers.get(p.getUniqueId());
        pm.callEvent(new RPlayerLevelUPEvent(rp, rp.getLevel(), rp.getLevel() + level));
        rp.setLevel(rp.getLevel() + level);
    }

    public static void addExp(Player p, double exp) {
        RPlayer rp = rplayers.get(p.getUniqueId());
        double oexp = rp.getExp();
        double totalExp = rp.getExp() + exp;
        double rexp = levels.getDouble("LV." + (rp.getLevel()+1));
        if(rexp == 0) {
            System.out.println("rexp is 0");
            return;
        }
        if (totalExp >= rexp) {
            rp.setExp(totalExp - rexp);
            addLevel(p, 1);
        } else {
            rp.setExp(totalExp);
            pm.callEvent(new RPlayerExpGainEvent(rp, exp, oexp, totalExp));
        }
        System.out.println("exp is " + totalExp);
    }

    public static void subLevel(Player p, int level) {
        RPlayer rp = rplayers.get(p.getUniqueId());
        rp.setLevel(rp.getLevel() - level);
    }

    public static void subExp(Player p, double exp) {
        RPlayer rp = rplayers.get(p.getUniqueId());
        rp.setExp(rp.getExp() - exp);
    }

    public static void setLevel(Player p, int level) {
        rplayers.get(p.getUniqueId()).setLevel(level);
    }

    public static void setExp(Player p, double exp) {
        rplayers.get(p.getUniqueId()).setExp(exp);
    }

    public static int getLevel(Player p) {
        return rplayers.get(p.getUniqueId()).getLevel();
    }

    public static double getExp(Player p) {
        return rplayers.get(p.getUniqueId()).getExp();
    }

    public static void setBaseExp(CommandSender sender, String mob, String svalue) {
        if (MythicMobs.inst().getMobManager().getMobNames().contains(mob)) {
            try {
                double value = Double.parseDouble(svalue);
                config.set("Mobs." + mob + ".base", value);
                sender.sendMessage(prefix + mob + " 몹의 기본 경험치를 " + value + "로 설정하였습니다.");
                saveConfigs();
            } catch (NumberFormatException e) {
                sender.sendMessage(prefix + "옳바르지 않은 값입니다.");
            }
        } else {
            sender.sendMessage(prefix + "존재하지 않는 몹입니다.");
        }
    }

    public static void setPerLvExp(CommandSender sender, String mob, String svalue) {
        if (MythicMobs.inst().getMobManager().getMobNames().contains(mob)) {
            try {
                double value = Double.parseDouble(svalue);
                config.set("Mobs." + mob + ".perlv", value);
                sender.sendMessage(prefix + mob + " 몹의 레벨당 경험치 증가량을 " + value + "로 설정하였습니다.");
                saveConfigs();
            } catch (NumberFormatException e) {
                sender.sendMessage(prefix + "옳바르지 않은 값입니다.");
            }
        } else {
            sender.sendMessage(prefix + "존재하지 않는 몹입니다.");
        }
    }

    public static void reloadConfigs() {
        plugin.config = ConfigUtils.reloadPluginConfig(plugin, config);
    }

    public static void saveConfigs() {
        ConfigUtils.savePluginConfig(plugin, config);
    }
}
