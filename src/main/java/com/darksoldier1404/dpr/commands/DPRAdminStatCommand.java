package com.darksoldier1404.dpr.commands;

import com.darksoldier1404.dppc.utils.DataContainer;
import com.darksoldier1404.dpr.DRPG;
import com.darksoldier1404.dpr.functions.DRAUFunction;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("all")
public class DPRAdminStatCommand implements CommandExecutor, TabCompleter {
    private static final DRPG plugin = DRPG.getInstance();
    private static final DataContainer data = plugin.data;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(data.getPrefix() + "관리자 전용 명령어 입니다.");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(data.getPrefix() + "/dprs line <1~6> : 스텟창의 행을 설정합니다.");
            sender.sendMessage(data.getPrefix() + "/dprs getitem : 스텟창 설정 아이템을 받습니다.");
            sender.sendMessage(data.getPrefix() + "/dprs display : 스텟창을 설정합니다.");
            sender.sendMessage(data.getPrefix() + "/dprs add <유저> <스텟> : 해당 유저의 스텟을 올립니다.");
            sender.sendMessage(data.getPrefix() + "/dprs sub <유저> <스텟> : 해당 유저의 스텟을 내립니다.");
            sender.sendMessage(data.getPrefix() + "/dprs set <유저> <스텟> : 해당 유저의 스텟을 설정합니다.");
            sender.sendMessage(data.getPrefix() + "/dprs get <유저> : 해당 유저의 스텟을 확인합니다.");
            return true;
        }
        if (args.length == 1) {
            sender.sendMessage(data.getPrefix() + "옵션을 선택해주세요.");
            sender.sendMessage(data.getPrefix() + "line, getitem, display, add, sub, set, get");
            return false;
        }
        if (args[0].equalsIgnoreCase("line")) {
            if (args.length == 2) {
                sender.sendMessage(data.getPrefix() + "줄 수를 입력해주세요, 1~6 사이입니다.");
                return false;
            }
            if (args.length == 3) {
                try {
                    int line = Integer.parseInt(args[1]);
                    if (line <= 1 || line >= 6) {
                        data.getConfig().set("Settings.StatsGUILine", line);
                        return false;
                    } else {
                        sender.sendMessage(data.getPrefix() + "줄은 1~6 이여야 합니다.");
                        return false;
                    }
                } catch (NumberFormatException e) {
                    sender.sendMessage(data.getPrefix() + "옳바르지 않은 숫자입니다.");
                    return false;
                }
            }
            return false;
        }
        if (args[0].equalsIgnoreCase("getitem")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(data.getPrefix() + "플레이어만 사용 가능한 명령어 입니다.");
                return false;
            }
            DRAUFunction.getAllStatsItems((Player) sender);
            return false;
        }
        if (args[0].equalsIgnoreCase("display")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(data.getPrefix() + "플레이어만 사용 가능한 명령어 입니다.");
                return false;
            }
            DRAUFunction.openStatsItemSettings((Player) sender);
            return false;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            return null;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("stat")) {
                return Arrays.asList("display", "line", "add", "sub", "set", "get", "getitem");
            }
        }
        if (args.length == 2) {
            if (!args[1].equalsIgnoreCase("get") && !args[1].equalsIgnoreCase("display") && !args[1].equalsIgnoreCase("line")) {
                return Bukkit.getOnlinePlayers().stream().map(player -> player.getName()).collect(java.util.stream.Collectors.toList());
            }
        }
        return null;
    }
}
