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
public class DPRAdminStatCommand implements CommandExecutor, TabCompleter {
    private static final DRPG plugin = DRPG.getInstance();
    private static final DataContainer data = plugin.data;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_permission_required"));
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dprs_info_line"));
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dprs_info_getitem"));
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dprs_info_display"));
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dprs_info_add"));
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dprs_info_sub"));
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dprs_info_set"));
            return true;
        }
        if (args[0].equalsIgnoreCase("line")) {
            if (args.length == 1) {
                sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dprs_line_required"));
                return false;
            }
            if (args.length == 2) {
                try {
                    int line = Integer.parseInt(args[1]);
                    if (line <= 1 || line >= 6) {
                        data.getConfig().set("Settings.StatsGUILine", line);
                        return false;
                    } else {
                        sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dprs_line_should_be_1_to_6"));
                        return false;
                    }
                } catch (NumberFormatException e) {
                    sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_value_is_not_valid"));
                    return false;
                }
            }
            return false;
        }
        if (args[0].equalsIgnoreCase("getitem")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_player_only_command"));
                return false;
            }
            DRAUFunction.getAllStatsItems((Player) sender);
            return false;
        }
        if (args[0].equalsIgnoreCase("display")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_player_only_command"));
                return false;
            }
            DRAUFunction.openStatsItemSettings((Player) sender);
            return false;
        }
        if (args[0].equalsIgnoreCase("add")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_player_only_command"));
                return false;
            }
            if (args.length == 1) {
                sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_username_required"));
                return false;
            }
            if (args.length == 2) {
                sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dprs_stats_required"));
                return false;
            }
            if (args.length == 3) {
                sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dprs_point_required"));
                return false;
            }
            if (args.length == 4) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_user_is_not_exist"));
                    return false;
                }
                try {
                    int value = Integer.parseInt(args[3]);
                    DRAUFunction.addStat((Player) sender, args[2], value, target);
                    return false;
                } catch (Exception e) {
                    sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_value_is_not_valid"));
                    return false;
                }
            }
        }
        if (args[0].equalsIgnoreCase("sub")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_player_only_command"));
                return false;
            }
            if (args.length == 1) {
                sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_username_required"));
                return false;
            }
            if (args.length == 2) {
                sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dprs_stats_required"));
                return false;
            }
            if (args.length == 3) {
                sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dprs_point_required"));
                return false;
            }
            if (args.length == 4) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_user_is_not_exist"));
                    return false;
                }
                try {
                    int value = Integer.parseInt(args[3]);
                    DRAUFunction.subStat((Player) sender, args[2], value, target);
                    return false;
                } catch (Exception e) {
                    sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_value_is_not_valid"));
                    return false;
                }
            }
        }
        if (args[0].equalsIgnoreCase("set")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_player_only_command"));
                return false;
            }
            if (args.length == 1) {
                sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_username_required"));
                return false;
            }
            if (args.length == 2) {
                sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dprs_stats_required"));
                return false;
            }
            if (args.length == 3) {
                sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dprs_point_required"));
                return false;
            }
            if (args.length == 4) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_user_is_not_exist"));
                    return false;
                }
                DRAUFunction.setStat((Player) sender, args[2], args[3], target);
                return false;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            return null;
        }
        if (args.length == 1) {
            return Arrays.asList("display", "line", "add", "sub", "set", "getitem");
        }
        if (args.length == 2) {
            if (!args[1].equalsIgnoreCase("get") && !args[1].equalsIgnoreCase("display") && !args[1].equalsIgnoreCase("line")) {
                return Bukkit.getOnlinePlayers().stream().map(player -> player.getName()).collect(java.util.stream.Collectors.toList());
            }
        }
        return null;
    }
}
