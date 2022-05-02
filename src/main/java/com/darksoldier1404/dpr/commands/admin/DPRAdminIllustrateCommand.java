package com.darksoldier1404.dpr.commands.admin;

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
        if (!sender.isOp()) {
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_permission_required"));
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dpri_info_chance"));
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dpri_info_set"));
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dpri_info_1"));
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dpri_info_2"));
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dpri_info_del"));
            return true;
        }
        if (args[0].equalsIgnoreCase("chance")) {
            if (args.length == 1) {
                sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_mob_name_required"));
                return false;
            }
            if (args.length == 2) {
                sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dpri_chance_required"));
                return false;
            }
            if (args.length == 3) {
                DRAUFunction.setChance(sender, args[1], args[2]);
                return false;
            }
            return false;
        }
        if (args[0].equalsIgnoreCase("set")) {
            if (args.length == 1) {
                sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_mob_name_required"));
                return false;
            }
            if (args.length == 2) {
                sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_prize_option_required"));
                return false;
            }
            if (args[2].equalsIgnoreCase("CMD")) {
                if (args.length == 3) {
                    sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_command_prize_insert"));
                    return false;
                }
                DRAUFunction.setCommandPrize(sender, args[1], args);
                return false;
            }
            if (args[2].equalsIgnoreCase("EXP")) {
                if (args.length == 3) {
                    sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_exp_prize_insert"));
                    return false;
                }
                DRAUFunction.setExpPrize(sender, args[1], args[3]);
                return false;
            }
            return false;
        }
        if (args[0].equalsIgnoreCase("del")) {
            if (args.length == 1) {
                sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_mob_name_required"));
                return false;
            }
            if (args.length == 2) {
                sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_prize_option_required"));
                return false;
            }
            if (args[2].equalsIgnoreCase("CMD")) {
                DRAUFunction.removeCommandPrize(sender, args[1]);
                return false;
            }
            if (args[2].equalsIgnoreCase("EXP")) {
                DRAUFunction.removeExpPrize(sender, args[1]);
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
            return Arrays.asList("chance", "set", "del");
        }
        if (args.length == 2) {
            return MythicBukkit.inst().getMobManager().getMobNames().stream().collect(Collectors.toList());
        }
        return null;
    }
}
