package com.darksoldier1404.dpr.commands;

import com.darksoldier1404.dppc.utils.DataContainer;
import com.darksoldier1404.dpr.DRPG;
import com.darksoldier1404.dpr.functions.DRAUFunction;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public class DPRAdminMobCommand implements CommandExecutor, TabCompleter {
    private static final DRPG plugin = DRPG.getInstance();
    private static final DataContainer data = plugin.data;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.isOp()) {
            sender.sendMessage(data.getPrefix() + "관리자 전용 명령어 입니다.");
            return true;
        }
        if(args.length == 0) {
            sender.sendMessage(data.getPrefix() + "/dprm exp base <mob> <exp> : 해당 몹의 베이스 드랍 경험치를 설정합니다.");
            sender.sendMessage(data.getPrefix() + "/dprm exp perlv <mob> <exp> : 해당 몹의 레벨당 드랍 경험치를 설정합니다.");
            sender.sendMessage(data.getPrefix() + "EX) TestMob (base exp 100, perlv exp 50) LV.1 = 100, LV.3 = 200");
            sender.sendMessage(data.getPrefix() + "perlv 설정을 하지 않으면 모든 레벨에 동일한 base 경험치를 드랍합니다.");
            return true;
        }
        if(args[0].equalsIgnoreCase("exp")) {
            if(args.length == 1) {
                sender.sendMessage(data.getPrefix() + "base, perlv 둘 중 하나를 선택해주세요.");
                return true;
            }
            if(args.length == 2) {
                sender.sendMessage(data.getPrefix() + "몹을 선택해주세요.");
                return true;
            }
            if(args.length == 3) {
                sender.sendMessage(data.getPrefix() + "경험치를 설정해주세요.");
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
                sender.sendMessage(data.getPrefix() + "base, perlv 둘 중 하나를 선택해주세요.");
                return true;
            }
            sender.sendMessage(data.getPrefix() + "명령어가 옳바르지 않습니다.");
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
            return MythicBukkit.inst().getMobManager().getMobNames().stream().collect(Collectors.toList());
        }
        return null;
    }
}
