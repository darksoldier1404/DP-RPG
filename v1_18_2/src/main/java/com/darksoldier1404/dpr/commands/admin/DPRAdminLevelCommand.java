package com.darksoldier1404.dpr.commands.admin;

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
public class DPRAdminLevelCommand implements CommandExecutor, TabCompleter {
    private static final DRPG plugin = DRPG.getInstance();
    private static final DataContainer data = plugin.data;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_permission_required"));
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dprl_info_add"));
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dprl_info_sub"));
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dprl_info_set"));
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dprl_info_get"));
            return true;
        }
        if (args.length == 1) {
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dprl_option_required"));
            return false;
        }
        if (args.length == 2) {
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_username_required"));
            return false;
        }
        Player p = Bukkit.getPlayer(args[1]);
        if (p == null) {
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_user_is_not_exist"));
            return false;
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("get")) {
                sender.sendMessage(data.getPrefix() + data.getLang().getWithArgs("cmd_dprl_get_result", String.valueOf(DRAUFunction.getLevel(p))));
                return false;
            }
            try {
                int value = Integer.parseInt(args[2]);
                if (args[0].equalsIgnoreCase("add")) {
                    DRAUFunction.addLevel(p, value);
                    sender.sendMessage(data.getPrefix() + data.getLang().getWithArgs("cmd_dprl_add_result", String.valueOf(value)));
                    return false;
                }
                if (args[0].equalsIgnoreCase("sub")) {
                    DRAUFunction.subLevel(p, value);
                    sender.sendMessage(data.getPrefix() + data.getLang().getWithArgs("cmd_dprl_sub_result", String.valueOf(value)));
                    return false;
                }
                if (args[0].equalsIgnoreCase("set")) {
                    DRAUFunction.setLevel(p, value);
                    sender.sendMessage(data.getPrefix() + data.getLang().getWithArgs("cmd_dprl_set_result", String.valueOf(value)));
                    return false;
                }
            } catch (Exception e) {
                sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_value_is_not_valid"));
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
