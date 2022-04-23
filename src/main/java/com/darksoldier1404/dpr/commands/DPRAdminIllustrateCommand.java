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
public class DPRAdminIllustrateCommand implements CommandExecutor, TabCompleter {
    private static final DRPG plugin = DRPG.getInstance();
    private static final DataContainer data = plugin.data;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.isOp()) {
            sender.sendMessage(data.getPrefix() + "관리자 전용 명령어 입니다.");
            return true;
        }
        if(args.length == 0) {
            sender.sendMessage(data.getPrefix() + "/dpri chance <mob> <chance> - 해당 몹의 도감 조각 드롭 확률을 설정합니다.");
            sender.sendMessage(data.getPrefix() + "/dpri set <mob> CMD/EXP <CMD/EXP> - 해당 몹의 도감 완성 보상을 설정합니다.");
            sender.sendMessage(data.getPrefix() + "/dpri del <mob> CMD/EXP - 해당 몹의 도감 완성 보상을 삭제합니다.");
            return true;
        }
        if(args[0].equalsIgnoreCase("chance")) {
            if(args.length == 1) {
                sender.sendMessage(data.getPrefix() + "몹 이름을 입력해주세요.");
                return false;
            }
            if(args.length == 2) {
                sender.sendMessage(data.getPrefix() + "도감 조각 드롭 확률을 입력해주세요.");
                return false;
            }
            if(args.length == 3) {
                // todo coding function
                return false;
            }
            return false;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.isOp()) {
            return null;
        }
        if(args.length == 1) {
            return Arrays.asList("chance", "set", "del");
        }
        if(args.length == 2) {
            return MythicBukkit.inst().getMobManager().getMobNames().stream().collect(Collectors.toList());
        }
        return null;
    }
}
