package com.darksoldier1404.dpr.functions;

import com.darksoldier1404.dppc.utils.ConfigUtils;
import com.darksoldier1404.dppc.utils.DataContainer;
import com.darksoldier1404.dppc.utils.NBT;
import com.darksoldier1404.dpr.DRPG;
import com.darksoldier1404.dpr.enums.IllustrateType;
import com.darksoldier1404.dpr.enums.StatsType;
import com.darksoldier1404.dpr.events.obj.RPlayerExpGainEvent;
import com.darksoldier1404.dpr.events.obj.RPlayerLevelUPEvent;
import com.darksoldier1404.dpr.illustrate.Illustrate;
import com.darksoldier1404.dpr.rplayer.RPlayer;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@SuppressWarnings("all")
public class DRAUFunction {
    private static final DRPG plugin = DRPG.getInstance();
    private static final DataContainer data = plugin.data;
    private static final Map<UUID, RPlayer> rplayers = (Map<UUID, RPlayer>) data.get("rplayers");
    private static final PluginManager pm = Bukkit.getPluginManager();

    public static final Map<UUID, Boolean> currentOpenedisReadOnly = new HashMap<>();

    public static void addLevel(Player p, int level) {
        RPlayer rp = ((Map<UUID, RPlayer>) data.get("rplayers")).get(p.getUniqueId());
        pm.callEvent(new RPlayerLevelUPEvent(rp, rp.getLevel(), rp.getLevel() + level));
        rp.setLevel(rp.getLevel() + level);
        rp.getStat().setPoint(rp.getStat().getPoint() + plugin.levelUpStatPoint * level);

    }

    public static void addExp(Player p, double exp) {
        RPlayer rp = rplayers.get(p.getUniqueId());
        double oexp = rp.getExp();
        double totalExp = rp.getExp() + exp;
        double rexp = ((YamlConfiguration) data.get("levels")).getDouble("LV." + (rp.getLevel() + 1));
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

    public static void setStat(Player p, String stype, String svalue, Player target) {
        RPlayer rp = rplayers.get(target.getUniqueId());
        try{
            StatsType type = StatsType.valueOf(stype);
            switch (type){
                case HP: rp.getStat().setHp(Integer.parseInt(svalue)); break;
                case ARMOR: rp.getStat().setArmor(Integer.parseInt(svalue)); break;
                case CRITICAL_CHANCE: rp.getStat().setCriticalChance(Integer.parseInt(svalue)); break;
                case CRITICAL_DAMAGE: rp.getStat().setCriticalDamage(Integer.parseInt(svalue)); break;
                case DAMAGE: rp.getStat().setDamage(Integer.parseInt(svalue)); break;
                case LIFESTEAL: rp.getStat().setLifeSteal(Integer.parseInt(svalue)); break;
                case POINT: rp.getStat().setPoint(Integer.parseInt(svalue)); break;
                case PROJECTILE_ARMOR: rp.getStat().setProjectileArmor(Integer.parseInt(svalue)); break;
                case PROJECTILE_DAMAGE: rp.getStat().setProjectileDamage(Integer.parseInt(svalue)); break;
                case SPEED: rp.getStat().setSpeed(Integer.parseInt(svalue)); break;
            }
            p.sendMessage(data.getPrefix() + "?????? ?????????" + type.name() + " ????????? " + svalue + "?????? ?????????????????????.");
        }catch (Exception e){
            p.sendMessage(data.getPrefix() + "???????????? ?????? ?????? ????????? ???????????? ?????? ????????????.");
            return;
        }
    }

    public static void addStat(Player p, String stype, int svalue, Player target) {
        RPlayer rp = rplayers.get(target.getUniqueId());
        try{
            StatsType type = StatsType.valueOf(stype);
            switch (type){
                case HP: rp.getStat().setHp(rp.getStat().getHp() + svalue); break;
                case ARMOR: rp.getStat().setArmor(rp.getStat().getArmor() + svalue); break;
                case CRITICAL_CHANCE: rp.getStat().setCriticalChance(rp.getStat().getCriticalChance() + svalue); break;
                case CRITICAL_DAMAGE: rp.getStat().setCriticalDamage(rp.getStat().getCriticalDamage() + svalue); break;
                case DAMAGE: rp.getStat().setDamage(rp.getStat().getDamage() + svalue); break;
                case LIFESTEAL: rp.getStat().setLifeSteal(rp.getStat().getLifeSteal() + svalue); break;
                case POINT: rp.getStat().setPoint(rp.getStat().getPoint() + svalue); break;
                case PROJECTILE_ARMOR: rp.getStat().setProjectileArmor(rp.getStat().getProjectileArmor() + svalue); break;
                case PROJECTILE_DAMAGE: rp.getStat().setProjectileDamage(rp.getStat().getProjectileDamage() + svalue); break;
                case SPEED: rp.getStat().setSpeed(rp.getStat().getSpeed() + svalue); break;
            }
            p.sendMessage(data.getPrefix() + "?????? ?????????" + type.name() + " ????????? " + svalue + "?????? ???????????????.");
        }catch (Exception e){
            p.sendMessage(data.getPrefix() + "???????????? ?????? ?????? ????????? ???????????? ?????? ????????????.");
            return;
        }
    }

    public static void subStat(Player p, String stype, int svalue, Player target) {
        RPlayer rp = rplayers.get(target.getUniqueId());
        try{
            StatsType type = StatsType.valueOf(stype);
            switch (type){
                case HP: rp.getStat().setHp(rp.getStat().getHp() - svalue); break;
                case ARMOR: rp.getStat().setArmor(rp.getStat().getArmor() - svalue); break;
                case CRITICAL_CHANCE: rp.getStat().setCriticalChance(rp.getStat().getCriticalChance() - svalue); break;
                case CRITICAL_DAMAGE: rp.getStat().setCriticalDamage(rp.getStat().getCriticalDamage() - svalue); break;
                case DAMAGE: rp.getStat().setDamage(rp.getStat().getDamage() - svalue); break;
                case LIFESTEAL: rp.getStat().setLifeSteal(rp.getStat().getLifeSteal() - svalue); break;
                case POINT: rp.getStat().setPoint(rp.getStat().getPoint() - svalue); break;
                case PROJECTILE_ARMOR: rp.getStat().setProjectileArmor(rp.getStat().getProjectileArmor() - svalue); break;
                case PROJECTILE_DAMAGE: rp.getStat().setProjectileDamage(rp.getStat().getProjectileDamage() - svalue); break;
                case SPEED: rp.getStat().setSpeed(rp.getStat().getSpeed() - svalue); break;
            }
            p.sendMessage(data.getPrefix() + "?????? ?????????" + type.name() + " ????????? " + svalue + "???????????????.");
        }catch (Exception e){
            p.sendMessage(data.getPrefix() + "???????????? ?????? ?????? ????????? ???????????? ?????? ????????????.");
            return;
        }
    }

    public static void addIllustrate(Player p, ItemStack item) {
        RPlayer rp = rplayers.get(p.getUniqueId());
        if (NBT.hasTagKey(item, "dpri")) {
            String mob = NBT.getStringTag(item, "dpri");
            int num = NBT.getIntegerTag(item, "dpri_" + mob);
            boolean b;
            if (rp.getIllustrate().containsKey(mob)) {
                Illustrate ill = rp.getIllustrate().get(mob);
                b = ill.addSeal(IllustrateType.valueOf(num));
            } else {
                Illustrate ill = new Illustrate(mob);
                b = ill.addSeal(IllustrateType.valueOf(num));
                rp.getIllustrate().put(mob, ill);
            }
            if (b) {
                p.sendMessage(data.getPrefix() + "?????? ????????? ?????????????????????.");
                item.setAmount(item.getAmount() - 1);
            } else {
                p.sendMessage(data.getPrefix() + "?????? ?????? ????????? ???????????? ????????????.");
            }
        }
    }

    public static void setBaseExp(CommandSender sender, String mob, String svalue) {
        if (MythicBukkit.inst().getMobManager().getMobNames().contains(mob)) {
            try {
                double value = Double.parseDouble(svalue);
                data.getConfig().set("Mobs." + mob + ".base", value);
                sender.sendMessage(data.getPrefix() + mob + " ?????? ?????? ???????????? " + value + "??? ?????????????????????.");
                saveConfigs();
            } catch (NumberFormatException e) {
                sender.sendMessage(data.getPrefix() + "???????????? ?????? ????????????.");
            }
        } else {
            sender.sendMessage(data.getPrefix() + "???????????? ?????? ????????????.");
        }
    }

    public static void setPerLvExp(CommandSender sender, String mob, String svalue) {
        if (MythicBukkit.inst().getMobManager().getMobNames().contains(mob)) {
            try {
                double value = Double.parseDouble(svalue);
                data.getConfig().set("Mobs." + mob + ".perlv", value);
                sender.sendMessage(data.getPrefix() + mob + " ?????? ????????? ????????? ???????????? " + value + "??? ?????????????????????.");
                saveConfigs();
            } catch (NumberFormatException e) {
                sender.sendMessage(data.getPrefix() + "???????????? ?????? ????????????.");
            }
        } else {
            sender.sendMessage(data.getPrefix() + "???????????? ?????? ????????????.");
        }
    }

    public static void setChance(CommandSender sender, String mob, String svalue) {
        if (MythicBukkit.inst().getMobManager().getMobNames().contains(mob)) {
            try {
                double value = Double.parseDouble(svalue);
                data.getConfig().set("Mobs." + mob + ".chance", value);
                sender.sendMessage(data.getPrefix() + mob + " ?????? ?????? ?????? ?????? ????????? " + value + "??? ?????????????????????.");
                saveConfigs();
            } catch (NumberFormatException e) {
                sender.sendMessage(data.getPrefix() + "???????????? ?????? ????????????.");
            }
        } else {
            sender.sendMessage(data.getPrefix() + "???????????? ?????? ????????????.");
        }
    }

    public static void setExpPrize(CommandSender sender, String mob, String svalue) {
        if (MythicBukkit.inst().getMobManager().getMobNames().contains(mob)) {
            try {
                double value = Double.parseDouble(svalue);
                data.getConfig().set("Mobs." + mob + ".expPrize", value);
                sender.sendMessage(data.getPrefix() + mob + " ?????? ????????? ????????? " + value + "??? ?????????????????????.");
                saveConfigs();
            } catch (NumberFormatException e) {
                sender.sendMessage(data.getPrefix() + "???????????? ?????? ????????????.");
            }
        } else {
            sender.sendMessage(data.getPrefix() + "???????????? ?????? ????????????.");
        }
    }

    public static void removeExpPrize(CommandSender sender, String mob) {
        if (MythicBukkit.inst().getMobManager().getMobNames().contains(mob)) {
            data.getConfig().set("Mobs." + mob + ".expPrize", null);
            sender.sendMessage(data.getPrefix() + mob + " ?????? ????????? ????????? ?????????????????????.");
            saveConfigs();
        } else {
            sender.sendMessage(data.getPrefix() + "???????????? ?????? ????????????.");
        }
    }

    public static void setCommandPrize(CommandSender sender, String mob, String... svalue) {
        if (MythicBukkit.inst().getMobManager().getMobNames().contains(mob)) {
            try {
                String value = getText(svalue, 3);
                data.getConfig().set("Mobs." + mob + ".commandPrize", value);
                sender.sendMessage(data.getPrefix() + mob + " ?????? ????????? ????????? " + value + "??? ?????????????????????.");
                saveConfigs();
            } catch (NumberFormatException e) {
                sender.sendMessage(data.getPrefix() + "???????????? ?????? ????????????.");
            }
        } else {
            sender.sendMessage(data.getPrefix() + "???????????? ?????? ????????????.");
        }
    }

    public static void removeCommandPrize(CommandSender sender, String mob) {
        if (MythicBukkit.inst().getMobManager().getMobNames().contains(mob)) {
            data.getConfig().set("Mobs." + mob + ".commandPrize", null);
            sender.sendMessage(data.getPrefix() + mob + " ?????? ????????? ????????? ?????????????????????.");
            saveConfigs();
        } else {
            sender.sendMessage(data.getPrefix() + "???????????? ?????? ????????????.");
        }
    }

    public static double getExpPrize(String mob) {
        if (MythicBukkit.inst().getMobManager().getMobNames().contains(mob)) {
            return data.getConfig().getDouble("Mobs." + mob + ".expPrize");
        } else {
            return 0;
        }
    }

    public static String getCommandPrize(String mob) {
        if (MythicBukkit.inst().getMobManager().getMobNames().contains(mob)) {
            return data.getConfig().getString("Mobs." + mob + ".commandPrize");
        } else {
            return null;
        }
    }

    public static boolean hasIllustrate(String mob) {
        if (MythicBukkit.inst().getMobManager().getMobNames().contains(mob)) {
            return data.getConfig().isSet("Mobs." + mob + ".chance");
        } else {
            return false;
        }
    }

    @Nullable
    public static ItemStack getIllustratePeice(String mob) {
        if (data.getConfig().getDouble("Mobs." + mob + ".chance") != 0) {
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            // random 1 to 9
            int random = (int) (Math.random() * 9) + 1;
            meta.setDisplayName(ChatColor.GOLD + mob + " ?????? ?????? ??????" + random + "???");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.AQUA + "?????? ????????? ?????? ???????????? ??? ?????? ????????? ?????? ??? ????????????.");
            lore.add(ChatColor.RED + "?????? ?????? ?????? ?????? : " + data.getConfig().getDouble("Mobs." + mob + ".chance") + "%");
            lore.add(ChatColor.GOLD + "????????? ?????? ?????? ?????? ??????.");
            meta.setLore(lore);
            item.setItemMeta(meta);
            return NBT.setStringTag(NBT.setIntTag(item, "dpri_" + mob, random), "dpri", mob);
        }
        return null;
    }

    public static double getChance(String mob) {
        if (MythicBukkit.inst().getMobManager().getMobNames().contains(mob)) {
            return data.getConfig().getDouble("Mobs." + mob + ".chance");
        } else {
            return 0;
        }
    }

    public static boolean getCalcChance(String mob) {
        if (MythicBukkit.inst().getMobManager().getMobNames().contains(mob)) {
            double raw = data.getConfig().getDouble("Mobs." + mob + ".chance");
            // get result 100 of raw as random
            if (Math.random() * 100 < raw) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static void recivePrize(Player p, ItemStack item) {
        RPlayer rp = rplayers.get(p.getUniqueId());
        String mob = NBT.getStringTag(item, "dpri_prize_btn");
        if(rp.getIllustrate().get(mob).isRecivedPrize()){
            p.sendMessage(data.getPrefix() + "?????? ??? ?????? ?????? ????????? ???????????????.");
            return;
        }
        if (rp.getIllustrate().get(mob).getSealType().size() == 9) {
            boolean b = false;
            if (data.getConfig().isSet("Mobs." + mob + ".commandPrize")) {
                String command = data.getConfig().getString("Mobs." + mob + ".commandPrize");
                p.setOp(true);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("@p", p.getName()).replace("@P", p.getName()));
                p.setOp(false);
                p.sendMessage(data.getPrefix() + "????????? ????????? ???????????????!");
                b = true;
            }
            if (data.getConfig().isSet("Mobs." + mob + ".expPrize")) {
                addExp(p, data.getConfig().getDouble("Mobs." + mob + ".expPrize"));
                p.sendMessage(data.getPrefix() + "????????? ????????? ???????????????!");
                b = true;
            }
            if (!b) {
                p.sendMessage(data.getPrefix() + "?????? ????????? ????????? ????????????.");
            } else {
                rp.getIllustrate().get(mob).setRecivedPrize(true);
            }
        } else {
            p.sendMessage(data.getPrefix() + "?????? ?????? ????????? ???????????? ???????????????.");
        }
    }

    public static String getText(String[] args, int line) {
        StringBuilder s = new StringBuilder();
        args = Arrays.copyOfRange(args, line, args.length);
        Iterator<String> i = Arrays.stream(args).iterator();
        while (i.hasNext()) {
            s.append(i.next()).append(" ");
        }
        // delete last space
        if (s.charAt(s.length() - 1) == ' ') {
            s.deleteCharAt(s.length() - 1);
        }
        return s.toString();
    }

    public static void reloadConfigs() {
        data.reload();
        data.set("levels", ConfigUtils.loadCustomData(plugin, "levels"));
        data.set("stats", ConfigUtils.loadCustomData(plugin, "stats"));
        data.set("statsItems", ConfigUtils.loadCustomData(plugin, "statsItems"));
    }

    public static void saveConfigs() {
        ConfigUtils.savePluginConfig(plugin, data.getConfig());
        ConfigUtils.saveCustomData(plugin, ((YamlConfiguration) data.get("levels")), "levels");
        ConfigUtils.saveCustomData(plugin, ((YamlConfiguration) data.get("statsItems")), "statsItems");
        ConfigUtils.saveCustomData(plugin, ((YamlConfiguration) data.get("stats")), "stats");
    }

    public static void getAllStatsItems(Player p) {
        for (StatsType type : StatsType.values()) {
            ItemStack item = new ItemStack(Material.BOOK);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName("??b" + type.name());
            item.setItemMeta(im);
            item = NBT.setStringTag(item, "statsType", type.name());
            p.getInventory().addItem(item);
        }
    }

    public static void openStatsItemSettings(Player p) {
        Inventory inv = Bukkit.createInventory(null, 9 * data.getConfig().getInt("Settings.StatsGUILine"), "?????? ????????? ??????");
        for (int i = 0; i < 9 * data.getConfig().getInt("Settings.StatsGUILine"); i++) {
            inv.setItem(i, ((YamlConfiguration) data.get("statsItems")).getItemStack("ItemStack.StatsItems." + i));
        }
        p.openInventory(inv);
    }

    public static void saveStatsItemSettings(Player p, Inventory inv) {
        for (int i = 0; i < inv.getSize(); i++) {
            ((YamlConfiguration) data.get("statsItems")).set("ItemStack.StatsItems." + i, inv.getItem(i));
        }
        p.sendMessage(data.getPrefix() + "?????? ????????? ????????? ?????????????????????.");
    }

    public static void openStatGUI(Player p, boolean readOnly, Player target) {
        Inventory inv = Bukkit.createInventory(null, 9 * data.getConfig().getInt("Settings.StatsGUILine"), p.getName() + "??? ?????? ??????");
        currentOpenedisReadOnly.put(target.getUniqueId(), readOnly);
        for (int i = 0; i < 9 * data.getConfig().getInt("Settings.StatsGUILine"); i++) {
            ItemStack item = ((YamlConfiguration) data.get("statsItems")).getItemStack("ItemStack.StatsItems." + i);
            item = item == null ? new ItemStack(Material.AIR) : item.clone();
            if (NBT.hasTagKey(item, "statsType")) {
                item = initPlaceholder(StatsType.valueOf(NBT.getStringTag(item, "statsType")), ((Map<UUID, RPlayer>) data.get("rplayers")).get(p.getUniqueId()), item);
                inv.setItem(i, item);
                continue;
            }
            inv.setItem(i, item);
        }
        p.openInventory(inv);
    }

    public static void updateStat(Player p, Inventory inv) {
        for (int i = 0; i < 9 * data.getConfig().getInt("Settings.StatsGUILine"); i++) {
            ItemStack item = ((YamlConfiguration) data.get("statsItems")).getItemStack("ItemStack.StatsItems." + i);
            item = item == null ? new ItemStack(Material.AIR) : item.clone();
            if (NBT.hasTagKey(item, "statsType")) {
                item = initPlaceholder(StatsType.valueOf(NBT.getStringTag(item, "statsType")), ((Map<UUID, RPlayer>) data.get("rplayers")).get(p.getUniqueId()), item);
                inv.setItem(i, item);
                continue;
            }
            inv.setItem(i, item);
        }
    }

    public static ItemStack initPlaceholder(StatsType type, RPlayer rp, ItemStack item) {
        if (item.hasItemMeta()) {
            if (!item.getItemMeta().hasLore()) return item;
        }
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(im.getDisplayName().replace("<stat>", String.valueOf(rp.getStat().getStat(type))));
        List<String> lore = im.getLore();
        for (int i = 0; i < lore.size(); i++) {
            lore.set(i, lore.get(i).replace("<stat>", String.valueOf(rp.getStat().getStat(type))));
        }
        im.setLore(lore);
        item.setItemMeta(im);
        return item;
    }

    public static void openIllustrateGUI(Player p, String mob) {
        RPlayer rp = rplayers.get(p.getUniqueId());
        Inventory inv = Bukkit.createInventory(null, 54, "??b" + mob + "??? ?????? ??????");
        ItemStack pane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, pane);
        }
        ItemStack ill = new ItemStack(Material.PAPER);
        inv.setItem(12, getIllustrateStack(ill, 1, rp, mob));
        inv.setItem(13, getIllustrateStack(ill, 2, rp, mob));
        inv.setItem(14, getIllustrateStack(ill, 3, rp, mob));
        inv.setItem(21, getIllustrateStack(ill, 4, rp, mob));
        inv.setItem(22, getIllustrateStack(ill, 5, rp, mob));
        inv.setItem(23, getIllustrateStack(ill, 6, rp, mob));
        inv.setItem(30, getIllustrateStack(ill, 7, rp, mob));
        inv.setItem(31, getIllustrateStack(ill, 8, rp, mob));
        inv.setItem(32, getIllustrateStack(ill, 9, rp, mob));
        ItemStack prize = new ItemStack(Material.EMERALD);
        ItemMeta im = prize.getItemMeta();
        im.setDisplayName("??a?????? ?????? ?????? ??????");
        im.setLore(Arrays.asList("", "??6?????? ????????? ?????????????????? ???????????? ????????? ???????????????!"));
        prize.setItemMeta(im);
        inv.setItem(49, NBT.setStringTag(prize, "dpri_prize_btn", mob));
        p.openInventory(inv);
    }

    public static ItemStack getIllustrateStack(ItemStack item, int i, RPlayer rp, String mob) {
        ItemMeta im = item.getItemMeta();
        im.setDisplayName("??b" + mob + "??? " + i + "??? ?????? ??????");
        if (rp.getIllustrate().get(mob) != null) {
            Illustrate il = rp.getIllustrate().get(mob);
            im.setLore(Arrays.asList("", il.hasSeal(IllustrateType.valueOf(i)) ? "??6????????? ??????" : "??c???????????? ?????? ??????"));
            item.setItemMeta(im);
        }
        return item.clone();
    }
}
