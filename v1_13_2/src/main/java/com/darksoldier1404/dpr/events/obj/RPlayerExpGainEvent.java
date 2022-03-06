package com.darksoldier1404.dpr.events.obj;

import com.darksoldier1404.dpr.rplayer.RPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class RPlayerExpGainEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final double exp;
    private final double originalExp;
    private final double totalExp;
    private final RPlayer rplayer;

    public RPlayerExpGainEvent(final RPlayer rplayer, final double exp, final double originalExp, final double totalExp) {
        this.rplayer = rplayer;
        this.exp = exp;
        this.originalExp = originalExp;
        this.totalExp = totalExp;
    }

    public double getExp() {
        return exp;
    }

    public double getOriginalExp() {
        return originalExp;
    }

    public double getTotalExp() {
        return totalExp;
    }

    public RPlayer getRPlayer() {
        return rplayer;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
