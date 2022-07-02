package com.darksoldier1404.dpr.commands.user;

import com.darksoldier1404.dppc.utils.DataContainer;
import com.darksoldier1404.dpr.DRPG;
import com.darksoldier1404.dpr.functions.DRAUFunction;
import com.darksoldier1404.dpr.rplayer.RPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public class DPRUserIllustrateCommand implements CommandExecutor, TabCompleter {
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
            p.sendMessage(data.getPrefix() + data.getLang().get("cmd_dillustrate_info_open"));
            return false;
        }
        if (args[0].equals("오픈") || args[0].equalsIgnoreCase("open")) {
            if(args.length == 1) {
                p.sendMessage(data.getPrefix() + data.getLang().get("cmd_etc_mob_name_required"));
                return false;
            }
            DRAUFunction.openIllustrateGUI(p, args[1]);
            return false;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            return null;
        }
        Player p = (Player) sender;
        if (args.length == 1) {
            return Arrays.asList("오픈", "open");
        }
        if(args.length == 2) {
            return ((Map<UUID, RPlayer>) data.get("rplayers")).get(p.getUniqueId()).getIllustrate().keySet().stream().collect(Collectors.toList());
        }
        return null;
    }
}
