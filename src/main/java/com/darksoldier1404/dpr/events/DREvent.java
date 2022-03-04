package com.darksoldier1404.dpr.events;

import com.darksoldier1404.dppc.api.inventory.DInventory;
import com.darksoldier1404.dppc.utils.ConfigUtils;
import com.darksoldier1404.dppc.utils.NBT;
import com.darksoldier1404.dpr.DRPG;
import com.darksoldier1404.dpr.events.obj.RPlayerExpGainEvent;
import com.darksoldier1404.dpr.events.obj.RPlayerLevelUPEvent;
import com.darksoldier1404.dpr.events.obj.StatLevelUPEvent;
import com.darksoldier1404.dpr.functions.DRASFunction;
import com.darksoldier1404.dpr.functions.DRAUFunction;
import com.darksoldier1404.dpr.rplayer.RPlayer;
import com.darksoldier1404.dpr.rplayer.StatsType;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.UUID;

@SuppressWarnings("all")
public class DREvent implements Listener {
    private final DRPG plugin = DRPG.getInstance();
    private final Map<UUID, RPlayer> rplayers = plugin.rplayers;
    private final YamlConfiguration config = plugin.config;
    private final YamlConfiguration levels = plugin.levels;

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (ConfigUtils.createCustomData(plugin, p.getUniqueId() + ".yml", "rplayers") != null) {
            YamlConfiguration data = ConfigUtils.loadCustomData(plugin, e.getPlayer().getUniqueId() + ".yml", "rplayers");
            RPlayer rp = new RPlayer(p, data, config);
            rplayers.put(e.getPlayer().getUniqueId(), rp);
            rp.getStat().init(p);
        } else {
            YamlConfiguration data = ConfigUtils.loadCustomData(plugin, e.getPlayer().getUniqueId() + ".yml", "rplayers");
            data.set("RPlayer.LV", 0);
            data.set("RPlayer.EXP", 0);
            RPlayer rp = new RPlayer(p, data, config);
            rplayers.put(e.getPlayer().getUniqueId(), rp);
            rp.getStat().init(p);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        RPlayer rp = rplayers.get(p.getUniqueId());
        rp.saveData();
        ConfigUtils.saveCustomData(plugin, rp.getData(), p.getUniqueId() + ".yml", "rplayers");
        rplayers.remove(p.getUniqueId());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        RPlayer rp = rplayers.get(e.getPlayer().getUniqueId());
        e.setFormat("§f[ §6LV.§e" + rp.getLevel() + " §f]" + e.getFormat());
    }

    @EventHandler
    public void onMobDeath(MythicMobDeathEvent e) {
        if (e.getKiller() instanceof Player) {
            Player p = (Player) e.getKiller();
            double level = e.getMobLevel();
            double base = config.getDouble("Mobs." + e.getMobType().getInternalName() + ".base");
            if(base == 0) return;
            double perlv = config.getDouble("Mobs." + e.getMobType().getInternalName() + ".perlv");
            if (perlv == 0) {
                DRAUFunction.addExp(p, base);
            } else {
                DRAUFunction.addExp(p, base + level * perlv);
            }
        }
    }

    @EventHandler
    public void onExpGain(RPlayerExpGainEvent e) {
        Player p = e.getRPlayer().getPlayer();
        int lv = e.getRPlayer().getLevel();
        double rexp = levels.getDouble("LV." + (lv + 1));
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§f[ §6LV.§e" + lv + " §6EXP.§e" + e.getOriginalExp() + "§6/§e" + rexp + " §b-> " + "§6LV.§e" + lv + " §6EXP.§e" + e.getTotalExp() + "§6/§e" + rexp + " §f]"));
    }

    @EventHandler
    public void onLevelUP(RPlayerLevelUPEvent e) {
        Player p = e.getRPlayer().getPlayer();
        int currentLevel = e.getCurrentLevel();
        int nextLevel = e.getNextLevel();
        double rexp = levels.getDouble("LV." + (nextLevel + 1));
        p.sendTitle("§f[ §6LV.§e" + currentLevel + "§b-> §6LV.§e" + nextLevel + " §f]", "§6EXP.§e" + e.getRPlayer().getExp() + "§6/§e" + rexp, 10, 20, 10);
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§f[ §6스텟포인트 §e" + plugin.levelUpStatPoint + " §6획득 §f]"));
    }

    @EventHandler
    public void onStatLevelUP(StatLevelUPEvent e) {
        e.getStats().init(e.getPlayer());
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getInventory() instanceof DInventory) {
            DInventory inv = (DInventory) e.getInventory();
            if (inv.isValidHandler(plugin)) {
                if (e.getView().getTitle().equals("스텟 아이템 설정")) {
                    DRAUFunction.saveStatsItemSettings((Player) e.getPlayer(), inv);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory() instanceof DInventory) {
            DInventory inv = (DInventory) e.getInventory();
            if (inv.isValidHandler(plugin)) {
                if (e.getView().getTitle().contains("스텟 정보")) {
                    e.setCancelled(true);
                    if ((boolean) inv.getObj()) return;
                    if (e.getCurrentItem() != null) {
                        if (NBT.hasTagKey(e.getCurrentItem(), "statsType")) {
                            StatsType type = StatsType.valueOf(NBT.getStringTag(e.getCurrentItem(), "statsType"));
                            DRASFunction.addStat(type, (Player) e.getWhoClicked());
                            DRAUFunction.openStatGUI((Player) e.getWhoClicked(), (boolean) inv.getObj());
                        }
                    }
                }
            }
        }
        plugin.stats.getConfigurationSection("asd");
    }
}