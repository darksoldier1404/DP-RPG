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
public class DPRAdminExpCommand implements CommandExecutor, TabCompleter {
    private static final DRPG plugin = DRPG.getInstance();
    private static final DataContainer data = plugin.data;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(data.getPrefix() + "관리자 전용 명령어 입니다.");
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage(data.getPrefix() + "/dpre add <유저> <경험치> : 해당 유저의 경험치를 올립니다.");
            sender.sendMessage(data.getPrefix() + "/dpre sub <유저> <경험치> : 해당 유저의 경험치를 내립니다.");
            sender.sendMessage(data.getPrefix() + "/dpre set <유저> <경험치> : 해당 유저의 경험치를 설정합니다.");
            sender.sendMessage(data.getPrefix() + "/dpre get <유저> : 해당 유저의 경험치를 확인합니다.");
            return false;
        }
        if (args.length == 1) {
            sender.sendMessage(data.getPrefix() + "add, sub, set, get 옵션을 입력해주세요.");
            return false;
        }
        if (args.length == 2) {
            sender.sendMessage(data.getPrefix() + "유저를 입력해주세요.");
            return false;
        }
        Player p = Bukkit.getPlayer(args[1]);
        if (p == null) {
            sender.sendMessage(data.getPrefix() + "해당 유저가 없습니다.");
            return false;
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("get")) {
                sender.sendMessage(data.getPrefix() + "해당 유저의 경험치는 " + DRAUFunction.getExp(p) + "입니다.");
                return false;
            }
            try {
                double value = Double.parseDouble(args[2]);
                if (args[0].equalsIgnoreCase("add")) {
                    DRAUFunction.addExp(p, value);
                    sender.sendMessage(data.getPrefix() + "해당 유저의 경험치를 " + value + "만큼 올렸습니다.");
                    return false;
                }
                if (args[0].equalsIgnoreCase("sub")) {
                    DRAUFunction.subExp(p, value);
                    sender.sendMessage(data.getPrefix() + "해당 유저의 경험치를 " + value + "만큼 내렸습니다.");
                    return false;
                }
                if (args[0].equalsIgnoreCase("set")) {
                    DRAUFunction.setExp(p, value);
                    sender.sendMessage(data.getPrefix() + "해당 유저의 경험치를 " + value + "으로 설정하였습니다.");
                    return false;
                }
            } catch (Exception e) {
                sender.sendMessage(data.getPrefix() + "올바른 값을 입력해주세요.");
                return false;
            }
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
            if (args[0].equalsIgnoreCase("get")) {
                return Bukkit.getOnlinePlayers().stream().map(player -> player.getName()).collect(java.util.stream.Collectors.toList());
            }
            return Arrays.asList("add", "sub", "set", "get");
        }
        if (args.length == 2) {
            if (!args[0].equalsIgnoreCase("get")) {
                return Bukkit.getOnlinePlayers().stream().map(player -> player.getName()).collect(java.util.stream.Collectors.toList());
            }
        }
        return null;
    }
}
