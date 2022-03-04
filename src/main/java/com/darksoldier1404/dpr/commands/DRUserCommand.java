package com.darksoldier1404.dpr.commands;

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
public class DRUserCommand implements CommandExecutor, TabCompleter {
    private final DRPG plugin = DRPG.getInstance();
    private final String prefix = plugin.prefix;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(prefix + "인게임에서만 사용할 수 있습니다.");
            return false;
        }
        Player p = (Player) sender;
        if (args.length == 0) {
            p.sendMessage(prefix + "/스텟 오픈 : 스텟창을 엽니다.");
            p.sendMessage(prefix + "/스텟 오픈 <닉네임> : 다른 유저의 스텟창을 엽니다. (읽기 전용)");
            p.sendMessage(prefix + "/스텟 공개 : 다른 유저가 자신의 스텟 정보를 볼 수 있도록 설정합니다. (토글)");
            return false;
        }
        if (args[0].equals("오픈")) {
            if (args.length == 1) {
                DRAUFunction.openStatGUI(p, false);
            } else if (args.length == 2) {
                Player target = plugin.getServer().getPlayer(args[1]);
                if (target == null) {
                    p.sendMessage(prefix + "해당 닉네임을 가진 유저가 없습니다.");
                    return false;
                }
                DRAUFunction.openStatGUI(target, true);
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("오픈", "공개");
        }
        return null;
    }
}
