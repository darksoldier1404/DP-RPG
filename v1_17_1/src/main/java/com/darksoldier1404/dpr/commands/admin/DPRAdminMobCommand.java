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
public class DPRAdminMobCommand implements CommandExecutor, TabCompleter {
    private static final DRPG plugin = DRPG.getInstance();
    private static final DataContainer data = plugin.data;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.isOp()) {
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_permission_required"));
            return true;
        }
        if(args.length == 0) {
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dprm_info_exp_base"));
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dprm_info_exp_perlv"));
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dprm_info_1"));
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dprm_info_2"));
            return true;
        }
        if(args[0].equalsIgnoreCase("exp")) {
            if(args.length == 1) {
                sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dprm_option_required"));
                return true;
            }
            if(args.length == 2) {
                sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_mob_name_required"));
                return true;
            }
            if(args.length == 3) {
                sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dprm_exp_required"));
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
                sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dprm_option_required"));
                return true;
            }
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_command_is_not_valid"));
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
