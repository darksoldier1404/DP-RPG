package com.darksoldier1404.dpr;

import com.darksoldier1404.dppc.utils.ConfigUtils;
import com.darksoldier1404.dppc.utils.DataContainer;
import com.darksoldier1404.dpr.functions.DRAUFunction;
import com.darksoldier1404.dpr.functions.InitManager;
import com.darksoldier1404.dpr.rplayer.RPlayer;
import com.darksoldier1404.dpr.rplayer.StatValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;

@SuppressWarnings("all")
public class DRPG extends JavaPlugin {
    private static DRPG plugin;
    public static DataContainer data;
    public StatValue statValue;
    public int levelUpStatPoint;

    public static DRPG getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        data = new DataContainer(plugin);
        InitManager.init();
    }

    @Override
    public void onDisable() {
        for(RPlayer rp : ((Map<UUID, RPlayer>)data.get("rplayers")).values()) {
            rp.saveData();
            ConfigUtils.saveCustomData(plugin, rp.getData(), rp.getPlayer().getUniqueId()+".yml", "rplayers");
        }
        DRAUFunction.saveConfigs();
    }
}
