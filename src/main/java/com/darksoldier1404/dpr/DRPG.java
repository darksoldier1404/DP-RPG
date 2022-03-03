package com.darksoldier1404.dpr;

import com.darksoldier1404.dppc.DPPCore;
import com.darksoldier1404.dppc.utils.ConfigUtils;
import com.darksoldier1404.dpr.commands.DRAdminCommand;
import com.darksoldier1404.dpr.commands.DRAdminMobCommand;
import com.darksoldier1404.dpr.commands.DRUserCommand;
import com.darksoldier1404.dpr.events.DREvent;
import com.darksoldier1404.dpr.rplayer.RPlayer;
import com.darksoldier1404.dpr.rplayer.StatValue;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("all")
public class DRPG extends JavaPlugin {
    private DPPCore core;
    private static DRPG plugin;
    public YamlConfiguration config;
    public YamlConfiguration levels;
    public YamlConfiguration stats;
    public YamlConfiguration statsItems;
    public String prefix;
    public Map<UUID, RPlayer> rplayers = new HashMap<>();
    public StatValue statValue;
    public int levelUpStatPoint;

    public static DRPG getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        Plugin pl = getServer().getPluginManager().getPlugin("DPP-Core");
        if(pl == null) {
            getLogger().warning("DPP-Core 플러그인이 설치되어있지 않습니다.");
            getLogger().warning("DPP-Core plugin is not installed.");
            plugin.setEnabled(false);
            return;
        }
        core = (DPPCore) pl;
        config = ConfigUtils.loadDefaultPluginConfig(plugin);
        prefix = ChatColor.translateAlternateColorCodes('&', config.getString("Settings.prefix"));
        saveResource("level.yml", false);
        saveResource("stats.yml", false);
        saveResource("statsItems.yml", false);
        levels = ConfigUtils.loadCustomData(plugin, "level");
        stats = ConfigUtils.loadCustomData(plugin, "stats");
        statsItems = ConfigUtils.loadCustomData(plugin, "statsItems");
        statValue = new StatValue(stats);
        levelUpStatPoint = stats.getInt("Stats.LevelUpStatPoint");
        getServer().getPluginManager().registerEvents(new DREvent(), plugin);
        getCommand("dpra").setExecutor(new DRAdminCommand());
        getCommand("dprm").setExecutor(new DRAdminMobCommand());
        getCommand("dstats").setExecutor(new DRUserCommand());
    }

    @Override
    public void onDisable() {
        for(RPlayer rp : rplayers.values()) {
            rp.saveData();
            ConfigUtils.saveCustomData(plugin, rp.getData(), rp.getPlayer().getUniqueId()+".yml", "rplayers");
        }
        rplayers.clear();
    }
}
