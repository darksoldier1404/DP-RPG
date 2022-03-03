package com.darksoldier1404.dpr.functions;

import com.darksoldier1404.dppc.utils.ConfigUtils;
import com.darksoldier1404.dpr.DRPG;
import com.darksoldier1404.dpr.events.obj.RPlayerExpGainEvent;
import com.darksoldier1404.dpr.events.obj.RPlayerLevelUPEvent;
import com.darksoldier1404.dpr.rplayer.RPlayer;
import com.darksoldier1404.dpr.rplayer.Stats;
import com.darksoldier1404.dpr.rplayer.StatsType;
import io.lumine.xikage.mythicmobs.MythicMobs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.util.Map;
import java.util.UUID;

public class DRASFunction {
    private static final DRPG plugin = DRPG.getInstance();
    private static final YamlConfiguration config = plugin.config;
    private static final YamlConfiguration levels = plugin.levels;
    private static final String prefix = plugin.prefix;
    private static final Map<UUID, RPlayer> rplayers = plugin.rplayers;
    private static final PluginManager pm = Bukkit.getPluginManager();

    public static void addStat(StatsType type, Player p) {
        RPlayer rp = rplayers.get(p.getUniqueId());
        Stats st = rp.getStat();
        if(type == StatsType.POINT) return;
        st.addStat(type, p);
    }
}
