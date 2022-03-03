package com.darksoldier1404.dpr.functions;

import com.darksoldier1404.dppc.api.inventory.DInventory;
import com.darksoldier1404.dppc.utils.ConfigUtils;
import com.darksoldier1404.dppc.utils.NBT;
import com.darksoldier1404.dpr.DRPG;
import com.darksoldier1404.dpr.events.obj.RPlayerExpGainEvent;
import com.darksoldier1404.dpr.events.obj.RPlayerLevelUPEvent;
import com.darksoldier1404.dpr.rplayer.RPlayer;
import com.darksoldier1404.dpr.rplayer.StatsType;
import io.lumine.xikage.mythicmobs.MythicMobs;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class DRAUFunction {
    private static final DRPG plugin = DRPG.getInstance();
    private static final YamlConfiguration config = plugin.config;
    private static final YamlConfiguration levels = plugin.levels;
    private static final YamlConfiguration statsItems = plugin.statsItems;
    private static final String prefix = plugin.prefix;
    private static final Map<UUID, RPlayer> rplayers = plugin.rplayers;
    private static final PluginManager pm = Bukkit.getPluginManager();

    public static void addLevel(Player p, int level) {
        RPlayer rp = rplayers.get(p.getUniqueId());
        pm.callEvent(new RPlayerLevelUPEvent(rp, rp.getLevel(), rp.getLevel() + level));
        rp.setLevel(rp.getLevel() + level);
        rp.getStat().setPoint(rp.getStat().getPoint() + plugin.levelUpStatPoint);
    }

    public static void addExp(Player p, double exp) {
        RPlayer rp = rplayers.get(p.getUniqueId());
        double oexp = rp.getExp();
        double totalExp = rp.getExp() + exp;
        double rexp = levels.getDouble("LV." + (rp.getLevel() + 1));
        if (rexp == 0) return;
        if (totalExp >= rexp) {
            rp.setExp(totalExp - rexp);
            addLevel(p, 1);
        } else {
            rp.setExp(totalExp);
            pm.callEvent(new RPlayerExpGainEvent(rp, exp, oexp, totalExp));
        }
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

    public static void getAllStatsItems(Player p) {
        for (StatsType type : StatsType.values()) {
            ItemStack item = new ItemStack(Material.BOOK);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName("§b" + type.name());
            im.setLore(Arrays.asList("아이템 편집 플러그인으로 정보를 수정할 수 있습니다."
                    , "you can edit this item with the Item Edit plugins."));
            item.setItemMeta(im);
            item = NBT.setStringTag(item, "statsType", type.name());
            p.getInventory().addItem(item);
        }
    }

    public static void openStatsItemSettings(Player p) {
        DInventory inv = new DInventory(null, "스텟 아이템 설정", 9 * config.getInt("Settings.StatsGUILine"), plugin);
        for (int i = 0; i < 9 * config.getInt("Settings.StatsGUILine"); i++) {
            inv.setItem(i, statsItems.getItemStack("ItemStack.StatsItems." + i));
        }
        p.openInventory(inv);
    }

    public static void saveStatsItemSettings(Player p, DInventory inv) {
        for (int i = 0; i < inv.getSize(); i++) {
            statsItems.set("ItemStack.StatsItems." + i, inv.getItem(i));
        }
        p.sendMessage(prefix + "스텟 아이템 설정을 저장하였습니다.");
    }

    public static void openStatGUI(Player p, boolean readOnly) {
        DInventory inv = new DInventory(null, p.getName() + "의 스텟 정보", 9 * config.getInt("Settings.StatsGUILine"), plugin);
        inv.setObj(readOnly);
        for (int i = 0; i < 9 * config.getInt("Settings.StatsGUILine"); i++) {
            inv.setItem(i, statsItems.getItemStack("ItemStack.StatsItems." + i));
        }
        p.openInventory(inv);
    }
}
