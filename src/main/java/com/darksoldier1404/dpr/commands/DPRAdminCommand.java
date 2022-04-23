package com.darksoldier1404.dpr.commands;

import com.darksoldier1404.dppc.utils.DataContainer;
import com.darksoldier1404.dpr.DRPG;
import com.darksoldier1404.dpr.functions.DRAUFunction;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("all")
public class DPRAdminCommand implements CommandExecutor, TabCompleter {
    private static final DRPG plugin = DRPG.getInstance();
    private static final DataContainer data = plugin.data;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(data.getPrefix() + "관리자 전용 명령어 입니다.");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(data.getPrefix() + "/dpra - 관리자 명령어 목록");
            sender.sendMessage(data.getPrefix() + "/dprl - 관리자 레벨 명령어 목록");
            sender.sendMessage(data.getPrefix() + "/dpre - 관리자 경험치 명령어 목록");
            sender.sendMessage(data.getPrefix() + "/dprs - 관리자 스텟 명령어 목록");
            sender.sendMessage(data.getPrefix() + "/dprm - 관리자 몬스터 명령어 목록");
            sender.sendMessage(data.getPrefix() + "/dpra reload/rl : 모든 콘피그 설정을 리로드 합니다.");
            return true;
        }
        if (args[0].equalsIgnoreCase("rl") || args[0].equalsIgnoreCase("reload")) {
            DRAUFunction.reloadConfigs();
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            return null;
        }
        if (args.length == 1) {
            return Arrays.asList("rl", "reload");
        }
        return null;
    }
}
