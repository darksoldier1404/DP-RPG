package com.darksoldier1404.dpr.events;

import com.darksoldier1404.dppc.utils.ConfigUtils;
import com.darksoldier1404.dpr.DRPG;
import com.darksoldier1404.dpr.events.obj.RPlayerExpGainEvent;
import com.darksoldier1404.dpr.events.obj.RPlayerLevelUPEvent;
import com.darksoldier1404.dpr.functions.DRAUFunction;
import com.darksoldier1404.dpr.rplayer.RPlayer;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.UUID;

public class DREvent implements Listener {
    private final DRPG plugin = DRPG.getInstance();
    private final Map<UUID, RPlayer> rplayers = plugin.rplayers;
    private final YamlConfiguration config = plugin.config;
    private final YamlConfiguration levels = plugin.levels;

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(ConfigUtils.createCustomData(plugin, p.getUniqueId()+".yml", "rplayers") != null) {
            YamlConfiguration data = ConfigUtils.loadCustomData(plugin, e.getPlayer().getUniqueId()+".yml", "rplayers");
            RPlayer rp = new RPlayer(p, data, config);
            rplayers.put(e.getPlayer().getUniqueId(), rp);
        }else{
            YamlConfiguration data = ConfigUtils.loadCustomData(plugin, e.getPlayer().getUniqueId()+".yml", "rplayers");
            data.set("RPlayer.LV", 0);
            data.set("RPlayer.EXP", 0);
            RPlayer rp = new RPlayer(p, data, config);
            rplayers.put(e.getPlayer().getUniqueId(), rp);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        RPlayer rp = rplayers.get(p.getUniqueId());
        rp.saveData();
        ConfigUtils.saveCustomData(plugin, rp.getData(), p.getUniqueId()+".yml", "rplayers");
        rplayers.remove(p.getUniqueId());
    }

    @EventHandler
    public void onMobDeath(MythicMobDeathEvent e) {
        if(e.getKiller() instanceof Player) {
            Player p = (Player) e.getKiller();
            double level = e.getMobLevel();
            double base = config.getDouble("Mobs."+e.getMobType().getInternalName()+".base");
            double perlv = config.getDouble("Mobs."+e.getMobType().getInternalName()+".perlv");
            if(perlv == 0) {
                DRAUFunction.addExp(p, base);
            }else{
                DRAUFunction.addExp(p, base + level * perlv);
            }
        }
    }

    @EventHandler
    public void onExpGain(RPlayerExpGainEvent e) {
        Player p = e.getRPlayer().getPlayer();
        int lv = e.getRPlayer().getLevel();
        double rexp = levels.getDouble("LV."+(lv+1));
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§f[ §6LV.§e"+lv+" §6EXP.§e"+e.getOriginalExp()+"§6/§e"+rexp+" §b-> " + "§6LV.§e"+lv+" §6EXP.§e"+e.getTotalExp()+"§6/§e"+rexp+" §f]"));
    }

    @EventHandler
    public void onLevelUP(RPlayerLevelUPEvent e) {
        Player p = e.getRPlayer().getPlayer();
        int currentLevel = e.getCurrentLevel();
        int nextLevel = e.getNextLevel();
        double rexp = levels.getDouble("LV."+(nextLevel +1));
        p.sendTitle("§f[ §6LV.§e"+currentLevel + "§b-> §6LV.§e" + nextLevel+" §f]", "§6EXP.§e"+e.getRPlayer().getExp()+"§6/§e"+rexp, 10, 20, 10);
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§f[ §6스텟포인트 §e1 §6획득 §f]"));
    }
}
