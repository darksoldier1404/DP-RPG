package com.darksoldier1404.dpr.commands.admin;

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
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_permission_required"));
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dpra_info_dpra"));
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dpra_info_dprl"));
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dpra_info_dpre"));
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dpra_info_dprs"));
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dpra_info_dprm"));
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_dpra_info_reload"));
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
