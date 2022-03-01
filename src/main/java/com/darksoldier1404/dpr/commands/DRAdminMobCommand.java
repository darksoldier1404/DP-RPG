package com.darksoldier1404.dpr.commands;

import com.darksoldier1404.dpr.DRPG;
import com.darksoldier1404.dpr.functions.DRAUFunction;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("all")
public class DRAdminMobCommand implements CommandExecutor, TabCompleter {
    private final DRPG plugin = DRPG.getInstance();
    private final String prefix = plugin.prefix;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.isOp()) {
            sender.sendMessage(prefix + "관리자 전용 명령어 입니다.");
            return true;
        }
        if(args.length == 0) {
            sender.sendMessage(prefix + "/dprm exp base <mob> <exp> : 해당 몹의 베이스 드랍 경험치를 설정합니다.");
            sender.sendMessage(prefix + "/dprm exp perlv <mob> <exp> : 해당 몹의 레벨당 드랍 경험치를 설정합니다.");
            sender.sendMessage(prefix + "EX) TestMob (base exp 100, perlv exp 50) LV.1 = 100, LV.3 = 200");
            sender.sendMessage(prefix + "perlv 설정을 하지 않으면 모든 레벨에 동일한 base 경험치를 드랍합니다.");
            return true;
        }
        if(args[0].equalsIgnoreCase("exp")) {
            if(args.length == 1) {
                sender.sendMessage(prefix + "base, perlv 둘 중 하나를 선택해주세요.");
                return true;
            }
            if(args.length == 2) {
                sender.sendMessage(prefix + "몹을 선택해주세요.");
                return true;
            }
            if(args.length == 3) {
                sender.sendMessage(prefix + "경험치를 설정해주세요.");
                return true;
            }
            if(args.length == 4) {
                if(args[1].equalsIgnoreCase("base")) {
                    DRAUFunction.setBaseExp(sender, args[2], args[3]);
                    return true;
                }
                if(args[1].equalsIgnoreCase("perlv")) {
                    DRAUFunction.setPerLvExp(sender, args[2], args[3]);
                    return true;
                }
                sender.sendMessage(prefix + "base, perlv 둘 중 하나를 선택해주세요.");
                return true;
            }
            sender.sendMessage(prefix + "명령어가 옳바르지 않습니다.");
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.isOp()) {
            return null;
        }
        if(args.length == 1) {
            return Arrays.asList("exp");
        }
        if(args.length == 2) {
            return Arrays.asList("base", "perlv");
        }
        if(args.length == 3) {
            return MythicMobs.inst().getMobManager().getMobNames().stream().toList();
        }
        return null;
    }
}
