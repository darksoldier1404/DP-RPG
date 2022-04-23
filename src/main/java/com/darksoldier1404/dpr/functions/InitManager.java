package com.darksoldier1404.dpr.functions;

import com.darksoldier1404.dppc.utils.ConfigUtils;
import com.darksoldier1404.dppc.utils.DataContainer;
import com.darksoldier1404.dpr.DRPG;
import com.darksoldier1404.dpr.commands.*;
import com.darksoldier1404.dpr.events.DREvent;
import com.darksoldier1404.dpr.events.PlayerDamageEvent;
import com.darksoldier1404.dpr.rplayer.RPlayer;
import com.darksoldier1404.dpr.rplayer.StatValue;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.UUID;

@SuppressWarnings("all")
public class InitManager {
    private static final DRPG plugin = DRPG.getInstance();
    private static final DataContainer data = plugin.data;
    public static void init() {
        plugin.saveResource("level.yml", false);
        plugin.saveResource("stats.yml", false);
        plugin.saveResource("statsItems.yml", false);
        data.set("rplayers", new HashMap<UUID, RPlayer>());
        data.set("levels", ConfigUtils.loadCustomData(plugin, "level"));
        data.set("stats", ConfigUtils.loadCustomData(plugin, "stats"));
        data.set("statsItems", ConfigUtils.loadCustomData(plugin, "statsItems"));
        plugin.statValue = new StatValue((YamlConfiguration) data.get("stats"));
        plugin.levelUpStatPoint = ((YamlConfiguration)data.get("stats")).getInt("Stats.LevelUpStatPoint");
        plugin.getServer().getPluginManager().registerEvents(new DREvent(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerDamageEvent(), plugin);
        plugin.getCommand("dpra").setExecutor(new DPRAdminCommand());
        plugin.getCommand("dprl").setExecutor(new DPRAdminLevelCommand());
        plugin.getCommand("dpre").setExecutor(new DPRAdminExpCommand());
        plugin.getCommand("dprs").setExecutor(new DPRAdminStatCommand());
        plugin.getCommand("dprm").setExecutor(new DPRAdminMobCommand());
        plugin.getCommand("dpri").setExecutor(new DPRAdminIllustrateCommand());
    }
}
