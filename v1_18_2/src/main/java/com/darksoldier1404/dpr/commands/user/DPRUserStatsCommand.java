package com.darksoldier1404.dpr.commands.user;

import com.darksoldier1404.dppc.utils.DataContainer;
import com.darksoldier1404.dpr.DRPG;
import com.darksoldier1404.dpr.functions.DRAUFunction;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("all")
public class DPRUserStatsCommand implements CommandExecutor, TabCompleter {
    private static final DRPG plugin = DRPG.getInstance();
    private static final DataContainer data = plugin.data;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_player_only_command"));
            return false;
        }
        Player p = (Player) sender;
        if (args.length == 0) {
            p.sendMessage(data.getPrefix() + data.getLang().get("cmd_dstats_info_open"));
            if(p.isOp()) {
                p.sendMessage(data.getPrefix() + data.getLang().get("cmd_dstats_info_open_admin"));
            }
            return false;
        }
        if (args[0].equals("오픈") || args[0].equalsIgnoreCase("open")) {
            if (args.length == 1) {
                DRAUFunction.openStatGUI(p, false, p);
            } else if (args.length == 2) {
                if(p.isOp()){
                    Player target = plugin.getServer().getPlayer(args[1]);
                    if (target == null) {
                        p.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_user_is_not_exist"));
                        return false;
                    }
                    DRAUFunction.openStatGUI(p, true, target);
                }else{
                    p.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_permission_required"));
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("오픈", "open");
        }
        return null;
    }
}
