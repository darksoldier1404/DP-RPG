package com.darksoldier1404.dpr.functions;

import com.darksoldier1404.dppc.api.inventory.DInventory;
import com.darksoldier1404.dppc.utils.ConfigUtils;
import com.darksoldier1404.dppc.utils.NBT;
import com.darksoldier1404.dpr.DRPG;
import com.darksoldier1404.dpr.events.obj.RPlayerExpGainEvent;
import com.darksoldier1404.dpr.events.obj.RPlayerLevelUPEvent;
import com.darksoldier1404.dpr.rplayer.RPlayer;
import com.darksoldier1404.dpr.rplayer.StatsType;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;

import java.util.List;

@SuppressWarnings("all")
public class DRAUFunction {
    private static final DRPG plugin = DRPG.getInstance();
    private static final PluginManager pm = Bukkit.getPluginManager();

    public static void addLevel(Player p, int level) {
        RPlayer rp = plugin.rplayers.get(p.getUniqueId());
        pm.callEvent(new RPlayerLevelUPEvent(rp, rp.getLevel(), rp.getLevel() + level));
        rp.setLevel(rp.getLevel() + level);
        rp.getStat().setPoint(rp.getStat().getPoint() + plugin.levelUpStatPoint * level);
    }

    public static void addExp(Player p, double exp) {
        RPlayer rp = plugin.rplayers.get(p.getUniqueId());
        double oexp = rp.getExp();
        double totalExp = rp.getExp() + exp;
        double rexp = plugin.levels.getDouble("LV." + (rp.getLevel() + 1));
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
        RPlayer rp = plugin.rplayers.get(p.getUniqueId());
        rp.setLevel(rp.getLevel() - level);
    }

    public static void subExp(Player p, double exp) {
        RPlayer rp = plugin.rplayers.get(p.getUniqueId());
        rp.setExp(rp.getExp() - exp);
    }

    public static void setLevel(Player p, int level) {
        plugin.rplayers.get(p.getUniqueId()).setLevel(level);
    }

    public static void setExp(Player p, double exp) {
        plugin.rplayers.get(p.getUniqueId()).setExp(exp);
    }

    public static int getLevel(Player p) {
        return plugin.rplayers.get(p.getUniqueId()).getLevel();
    }

    public static double getExp(Player p) {
        return plugin.rplayers.get(p.getUniqueId()).getExp();
    }

    public static void setBaseExp(CommandSender sender, String mob, String svalue) {
        if (MythicBukkit.inst().getMobManager().getMobNames().contains(mob)) {
            try {
                double value = Double.parseDouble(svalue);
                plugin.config.set("Mobs." + mob + ".base", value);
                sender.sendMessage(plugin.prefix + mob + " 몹의 기본 경험치를 " + value + "로 설정하였습니다.");
                saveConfigs();
            } catch (NumberFormatException e) {
                sender.sendMessage(plugin.prefix + "옳바르지 않은 값입니다.");
            }
        } else {
            sender.sendMessage(plugin.prefix + "존재하지 않는 몹입니다.");
        }
    }

    public static void setPerLvExp(CommandSender sender, String mob, String svalue) {
        if (MythicBukkit.inst().getMobManager().getMobNames().contains(mob)) {
            try {
                double value = Double.parseDouble(svalue);
                plugin.config.set("Mobs." + mob + ".perlv", value);
                sender.sendMessage(plugin.prefix + mob + " 몹의 레벨당 경험치 증가량을 " + value + "로 설정하였습니다.");
                saveConfigs();
            } catch (NumberFormatException e) {
                sender.sendMessage(plugin.prefix + "옳바르지 않은 값입니다.");
            }
        } else {
            sender.sendMessage(plugin.prefix + "존재하지 않는 몹입니다.");
        }
    }

    public static void reloadConfigs() {
        plugin.config = ConfigUtils.reloadPluginConfig(plugin, plugin.config);
        plugin.levels = ConfigUtils.reloadPluginConfig(plugin, plugin.levels);
        plugin.stats = ConfigUtils.reloadPluginConfig(plugin, plugin.stats);
        plugin.statsItems = ConfigUtils.reloadPluginConfig(plugin, plugin.statsItems);
    }

    public static void saveConfigs() {
        ConfigUtils.savePluginConfig(plugin, plugin.config);
        ConfigUtils.saveCustomData(plugin, plugin.levels, "levels");
        ConfigUtils.saveCustomData(plugin, plugin.statsItems, "statsItems");
        ConfigUtils.saveCustomData(plugin, plugin.stats, "stats");
    }

    public static void getAllStatsItems(Player p) {
        for (StatsType type : StatsType.values()) {
            ItemStack item = new ItemStack(Material.BOOK);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName("§b" + type.name());
            item.setItemMeta(im);
            item = NBT.setStringTag(item, "statsType", type.name());
            p.getInventory().addItem(item);
        }
    }

    public static void openStatsItemSettings(Player p) {
        DInventory inv = new DInventory(null, "스텟 아이템 설정", 9 * plugin.config.getInt("Settings.StatsGUILine"), plugin);
        for (int i = 0; i < 9 * plugin.config.getInt("Settings.StatsGUILine"); i++) {
            inv.setItem(i, plugin.statsItems.getItemStack("ItemStack.StatsItems." + i));
        }
        p.openInventory(inv);
    }

    public static void saveStatsItemSettings(Player p, DInventory inv) {
        for (int i = 0; i < inv.getSize(); i++) {
            plugin.statsItems.set("ItemStack.StatsItems." + i, inv.getItem(i));
        }
        p.sendMessage(plugin.prefix + "스텟 아이템 설정을 저장하였습니다.");
    }

    public static void openStatGUI(Player p, boolean readOnly) {
        DInventory inv = new DInventory(null, p.getName() + "의 스텟 정보", 9 * plugin.config.getInt("Settings.StatsGUILine"), plugin);
        inv.setObj(readOnly);
        for (int i = 0; i < 9 * plugin.config.getInt("Settings.StatsGUILine"); i++) {
            ItemStack item = plugin.statsItems.getItemStack("ItemStack.StatsItems." + i).clone();
            if(NBT.hasTagKey(item, "statsType")) {
                item = initPlaceholder(StatsType.valueOf(NBT.getStringTag(item, "statsType")), plugin.rplayers.get(p.getUniqueId()), item);
                inv.setItem(i, item);
                continue;
            }
            inv.setItem(i, item);
        }
        p.openInventory(inv);
    }

    public static ItemStack initPlaceholder(StatsType type, RPlayer rp, ItemStack item) {
        if(item.hasItemMeta()) {
            if(!item.getItemMeta().hasLore()) return item;
        }
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(im.getDisplayName().replace("<stat>", String.valueOf(rp.getStat().getStat(type))));
        List<String> lore = im.getLore();
        for(int i = 0; i < lore.size(); i++) {
            lore.set(i, lore.get(i).replace("<stat>", String.valueOf(rp.getStat().getStat(type))));
        }
        im.setLore(lore);
        item.setItemMeta(im);
        return item;
    }
}
