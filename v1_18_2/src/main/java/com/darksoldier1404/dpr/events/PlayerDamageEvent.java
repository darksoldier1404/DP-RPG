package com.darksoldier1404.dpr.events;

import com.darksoldier1404.dppc.utils.DataContainer;
import com.darksoldier1404.dpr.DRPG;
import com.darksoldier1404.dpr.rplayer.RPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Map;
import java.util.UUID;

@SuppressWarnings("all")
public class PlayerDamageEvent implements Listener {
    private static final DRPG plugin = DRPG.getInstance();
    private static final DataContainer data = plugin.data;

    @EventHandler
    public void onDamaged(EntityDamageByEntityEvent e) {
        if(!(e.getEntity() instanceof Player)) return;
        if(e.getEntity().hasMetadata("NPC")) return;
        Player p = (Player) e.getEntity();
        Entity damager = e.getDamager();
        if(damager instanceof Projectile) {
            Projectile pj = (Projectile) damager;
            RPlayer rp = ((Map<UUID, RPlayer>) data.get("rplayers")).get(p.getUniqueId());
            int stat = rp.getStat().getProjectileArmor();
            if(stat == 0) return;
            double pArmor = plugin.statValue.getProjectileArmorPerStat()*stat;
            e.setDamage(e.getDamage() - pArmor);
        }else{
            RPlayer rp = ((Map<UUID, RPlayer>) data.get("rplayers")).get(p.getUniqueId());
            int stat = rp.getStat().getArmor();
            if(stat == 0) return;
            double pArmor = plugin.statValue.getArmorPerStat()*stat;
            e.setDamage(e.getDamage() - pArmor);
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        if(e.getEntity().hasMetadata("NPC")) return;
        if(e.getDamager() instanceof Projectile) {
            Projectile pj = (Projectile) e.getDamager();
            if(pj.getShooter() instanceof Player) {
                Player p = (Player) pj.getShooter();
                RPlayer rp = ((Map<UUID, RPlayer>) data.get("rplayers")).get(p.getUniqueId());
                int stat = rp.getStat().getProjectileDamage();
                if(stat == 0) return;
                double pDamage = plugin.statValue.getProjectileDamagePerStat()*stat;
                pDamage = calcCritical(rp, pDamage);
                e.setDamage(e.getDamage() + pDamage);
                lifeSteal(rp);
                return;
            }
        }
        if((e.getDamager() instanceof Player)) {
            Player p = (Player) e.getDamager();
            RPlayer rp = ((Map<UUID, RPlayer>) data.get("rplayers")).get(p.getUniqueId());
            int stat = rp.getStat().getDamage();
            if(stat == 0) return;
            double pDamage = plugin.statValue.getDamagePerStat()*stat;
            pDamage = calcCritical(rp, pDamage);
            e.setDamage(e.getDamage() + pDamage);
            lifeSteal(rp);
        }
    }

    public static double calcCritical(RPlayer rp, double damage) {
        int stat = rp.getStat().getCriticalChance();
        if(stat == 0) return damage;
        double crit = plugin.statValue.getCriticalChancePerStat()*stat;
        double critChance = crit/100;
        double random = Math.random();
        if(random <= critChance) {
            damage = damage*plugin.statValue.getCriticalDamagePerStat();
        }
        return damage;
    }

    public void lifeSteal(RPlayer rp) {
        Player p = rp.getPlayer();
        double health = p.getHealth();
        int stat = rp.getStat().getLifeSteal();
        if(stat == 0) return;
        double steal = plugin.statValue.getLifeStealPerStat()*stat;
        if(p.getMaxHealth() < health + steal) {
            p.setHealth(p.getMaxHealth());
        }else{
            p.setHealth(health + steal);
        }
    }
}
