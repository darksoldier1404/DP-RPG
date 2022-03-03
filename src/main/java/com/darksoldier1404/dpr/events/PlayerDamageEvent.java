package com.darksoldier1404.dpr.events;

import com.darksoldier1404.dpr.DRPG;
import com.darksoldier1404.dpr.rplayer.RPlayer;
import com.darksoldier1404.dpr.rplayer.StatValue;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Map;
import java.util.UUID;

public class PlayerDamageEvent implements Listener {
    private final DRPG plugin = DRPG.getInstance();
    private final Map<UUID, RPlayer> rplayers = plugin.rplayers;
    private final StatValue sv = plugin.statValue;
    @EventHandler
    public void onDamaged(EntityDamageByEntityEvent e) {
        if(!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        Entity damager = e.getDamager();
        if(damager instanceof Projectile) {
            Projectile pj = (Projectile) damager;
            RPlayer rp = rplayers.get(p.getUniqueId());
            int stat = rp.getStat().getProjectileArmor();
            if(stat == 0) return;
            double pArmor = sv.getProjectileArmorPerStat()*stat;
            e.setDamage(e.getDamage() - pArmor);
        }else{
            RPlayer rp = rplayers.get(p.getUniqueId());
            int stat = rp.getStat().getArmor();
            if(stat == 0) return;
            double pArmor = sv.getArmorPerStat()*stat;
            e.setDamage(e.getDamage() - pArmor);
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Projectile) {
            Projectile pj = (Projectile) e.getDamager();
            if(pj.getShooter() instanceof Player) {
                Player p = (Player) pj.getShooter();
                RPlayer rp = rplayers.get(p.getUniqueId());
                int stat = rp.getStat().getProjectileDamage();
                if(stat == 0) return;
                double pDamage = sv.getProjectileDamagePerStat()*stat;
                e.setDamage(e.getDamage() + pDamage);
                lifeSteal(rp);
                return;
            }
        }
        if((e.getDamager() instanceof Player)) {
            Player p = (Player) e.getDamager();
            RPlayer rp = rplayers.get(p.getUniqueId());
            int stat = rp.getStat().getDamage();
            if(stat == 0) return;
            double pDamage = sv.getDamagePerStat()*stat;
            e.setDamage(e.getDamage() + pDamage);
            lifeSteal(rp);
        }
    }

    public void lifeSteal(RPlayer rp) {
        Player p = rp.getPlayer();
        double health = p.getHealth();
        int stat = rp.getStat().getLifeSteal();
        if(stat == 0) return;
        double steal = sv.getLifeStealPerStat()*stat;
        if(p.getMaxHealth() < health + steal) {
            p.setHealth(p.getMaxHealth());
        }else{
            p.setHealth(health + steal);
        }
    }
}
