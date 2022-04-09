package com.darksoldier1404.dpr.commands;

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
public class DRAdminCommand implements CommandExecutor, TabCompleter {
    private final DRPG plugin = DRPG.getInstance();
    private final String prefix = plugin.prefix;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(prefix + "관리자 전용 명령어 입니다.");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(prefix + "/dpra lv add <유저> <레벨> : 해당 유저의 레벨을 올립니다.");
            sender.sendMessage(prefix + "/dpra lv sub <유저> <레벨> : 해당 유저의 레벨을 내립니다.");
            sender.sendMessage(prefix + "/dpra lv set <유저> <레벨> : 해당 유저의 레벨을 설정합니다.");
            sender.sendMessage(prefix + "/dpra lv get <유저> : 해당 유저의 레벨을 확인합니다.");
            sender.sendMessage(prefix + "/dpra exp add <유저> <경험치> : 해당 유저의 경험치를 올립니다.");
            sender.sendMessage(prefix + "/dpra exp sub <유저> <경험치> : 해당 유저의 경험치를 내립니다.");
            sender.sendMessage(prefix + "/dpra exp set <유저> <경험치> : 해당 유저의 경험치를 설정합니다.");
            sender.sendMessage(prefix + "/dpra exp get <유저> : 해당 유저의 경험치를 확인합니다.");
            sender.sendMessage(prefix + "/dpra stat line <1~6> : 스텟창의 행을 설정합니다.");
            sender.sendMessage(prefix + "/dpra stat getitem : 스텟창 설정 아이템을 받습니다.");
            sender.sendMessage(prefix + "/dpra stat display : 스텟창을 설정합니다.");
            sender.sendMessage(prefix + "/dpra stat add <유저> <스텟> : 해당 유저의 스텟을 올립니다.");
            sender.sendMessage(prefix + "/dpra stat sub <유저> <스텟> : 해당 유저의 스텟을 내립니다.");
            sender.sendMessage(prefix + "/dpra stat set <유저> <스텟> : 해당 유저의 스텟을 설정합니다.");
            sender.sendMessage(prefix + "/dpra stat check <유저> : 해당 유저의 스텟을 확인합니다.");
            sender.sendMessage(prefix + "/dpra reload/rl : 모든 콘피그 설정을 리로드 합니다.");
            return true;
        }
        if (args[0].equalsIgnoreCase("stat")) {
            if (args.length == 1) {
                sender.sendMessage(prefix + "옵션을 선택해주세요.");
                sender.sendMessage(prefix + "line, getitem, display, add, sub, set, check");
                return false;
            }
            if (args[1].equalsIgnoreCase("line")) {
                if (args.length == 2) {
                    sender.sendMessage(prefix + "줄 수를 입력해주세요, 1~6 사이입니다.");
                    return false;
                }
                if (args.length == 3) {
                    try {
                        int line = Integer.parseInt(args[2]);
                        if (line <= 1 || line >= 6) {
                            plugin.config.set("Settings.StatsGUILine", line);
                            return false;
                        } else {
                            sender.sendMessage(prefix + "줄은 1~6 이여야 합니다.");
                            return false;
                        }
                    } catch (NumberFormatException e) {
                        sender.sendMessage(prefix + "옳바르지 않은 숫자입니다.");
                        return false;
                    }
                }
                return false;
            }
            if (args[1].equalsIgnoreCase("getitem")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(prefix + "플레이어만 사용 가능한 명령어 입니다.");
                    return false;
                }
                DRAUFunction.getAllStatsItems((Player) sender);
                return false;
            }
            if (args[1].equalsIgnoreCase("display")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(prefix + "플레이어만 사용 가능한 명령어 입니다.");
                    return false;
                }
                DRAUFunction.openStatsItemSettings((Player) sender);
                return false;
            }
        }
        if (args[0].equalsIgnoreCase("rl") || args[0].equalsIgnoreCase("reload")) {
            DRAUFunction.reloadConfigs();
        }
        if (args[0].equalsIgnoreCase("lv") || args[0].equalsIgnoreCase("exp")) {
            if (args.length == 1) {
                sender.sendMessage(prefix + "add, sub, set, get 옵션을 입력해주세요.");
                return true;
            }
            if (args.length == 2) {
                if (args[1].equalsIgnoreCase("get")) {
                    sender.sendMessage(prefix + "유저를 입력해주세요.");
                    return true;
                }
                sender.sendMessage(prefix + "값을 입력해주세요.");
                return true;
            }
            if (args.length == 3) {
                if (args[1].equalsIgnoreCase("get")) {
                    Player player = Bukkit.getPlayer(args[2]);
                    if (player == null) {
                        sender.sendMessage(prefix + "해당 유저가 없습니다.");
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("lv")) {
                        sender.sendMessage(prefix + "해당 유저의 레벨은 " + DRAUFunction.getLevel(player) + "입니다.");
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("exp")) {
                        sender.sendMessage(prefix + "해당 유저의 경험치는 " + DRAUFunction.getExp(player) + "입니다.");
                        return true;
                    }
                    return true;
                }
                sender.sendMessage(prefix + "값을 입력해주세요.");
                return true;
            }
            Player p = Bukkit.getPlayer(args[2]);
            if (p == null) {
                sender.sendMessage(prefix + "해당 유저가 없습니다.");
                return true;
            }
            if (args.length == 4) {
                try {
                    double value = Double.parseDouble(args[3]);
                    if (args[1].equalsIgnoreCase("add")) {
                        if (args[0].equalsIgnoreCase("lv")) {
                            DRAUFunction.addLevel(p, (int) value);
                            sender.sendMessage(prefix + "해당 유저의 레벨을 " + (int) value + "만큼 올렸습니다.");
                            return true;
                        }
                        if (args[0].equalsIgnoreCase("exp")) {
                            DRAUFunction.addExp(p, value);
                            sender.sendMessage(prefix + "해당 유저의 경험치를 " + value + "만큼 올렸습니다.");
                            return true;
                        }
                    }
                    if (args[1].equalsIgnoreCase("sub")) {
                        if (args[0].equalsIgnoreCase("lv")) {
                            DRAUFunction.subLevel(p, (int) value);
                            sender.sendMessage(prefix + "해당 유저의 레벨을 " + (int) value + "만큼 내렸습니다.");
                            return true;
                        }
                        if (args[0].equalsIgnoreCase("exp")) {
                            DRAUFunction.subExp(p, (int) value);
                            sender.sendMessage(prefix + "해당 유저의 경험치를 " + (int) value + "만큼 내렸습니다.");
                            return true;
                        }
                    }
                    if (args[1].equalsIgnoreCase("set")) {
                        if (args[0].equalsIgnoreCase("lv")) {
                            DRAUFunction.setLevel(p, (int) value);
                            sender.sendMessage(prefix + "해당 유저의 레벨을 " + (int) value + "으로 설정하였습니다.");
                            return true;
                        }
                        if (args[0].equalsIgnoreCase("exp")) {
                            DRAUFunction.setExp(p, value);
                            sender.sendMessage(prefix + "해당 유저의 경험치를 " + value + "으로 설정하였습니다.");
                            return true;
                        }
                    }
                } catch (Exception e) {
                    sender.sendMessage(prefix + "올바른 값을 입력해주세요.");
                    return true;
                }
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
            return Arrays.asList("lv", "exp", "stat");
        }
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("get")) {
                return Bukkit.getOnlinePlayers().stream().map(player -> player.getName()).collect(java.util.stream.Collectors.toList());
            }
            if (args[0].equalsIgnoreCase("stat")) {
                return Arrays.asList("display", "line", "add", "sub", "set", "check", "getitem");
            }
            return Arrays.asList("add", "sub", "set", "get");
        }
        if (args.length == 3) {
            if (!args[1].equalsIgnoreCase("get") && !args[1].equalsIgnoreCase("display") && !args[1].equalsIgnoreCase("line")) {
                return Bukkit.getOnlinePlayers().stream().map(player -> player.getName()).collect(java.util.stream.Collectors.toList());
            }
        }
        return null;
    }
}
