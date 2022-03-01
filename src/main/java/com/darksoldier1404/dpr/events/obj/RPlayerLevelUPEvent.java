package com.darksoldier1404.dpr.events.obj;

import com.darksoldier1404.dpr.rplayer.RPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class RPlayerLevelUPEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final int currentLevel;
    private final int nextLevel;
    private final RPlayer rplayer;

    public RPlayerLevelUPEvent(final RPlayer rplayer, final int currentLevel, final int nextLevel) {
        this.rplayer = rplayer;
        this.currentLevel = currentLevel;
        this.nextLevel = nextLevel;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getNextLevel() {
        return nextLevel;
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
