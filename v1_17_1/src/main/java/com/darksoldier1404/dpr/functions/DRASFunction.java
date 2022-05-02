package com.darksoldier1404.dpr.functions;

import com.darksoldier1404.dpr.DRPG;
import com.darksoldier1404.dpr.rplayer.RPlayer;
import com.darksoldier1404.dpr.rplayer.Stats;
import com.darksoldier1404.dpr.enums.StatsType;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

@SuppressWarnings("all")
public class DRASFunction {
    private static final DRPG plugin = DRPG.getInstance();

    public static boolean addStat(StatsType type, Player p) {
        RPlayer rp = ((Map<UUID, RPlayer>)plugin.data.get("rplayers")).get(p.getUniqueId());
        Stats st = rp.getStat();
        if(type == StatsType.POINT) return false;
        return st.addStat(type, p);
    }
}
