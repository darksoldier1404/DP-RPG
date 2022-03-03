package com.darksoldier1404.dpr.events.obj;

import com.darksoldier1404.dpr.rplayer.RPlayer;
import com.darksoldier1404.dpr.rplayer.Stats;
import com.darksoldier1404.dpr.rplayer.StatsType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class StatLevelUPEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final Stats stats;
    private final StatsType type;

    public StatLevelUPEvent(final Player p, final Stats stats, final StatsType type) {
        this.player = p;
        this.stats = stats;
        this.type = type;
    }

    public Player getPlayer() {
        return player;
    }

    public Stats getStats() {
        return stats;
    }

    public StatsType getType() {
        return type;
    }

    @Override
    public @NotNull
    HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
