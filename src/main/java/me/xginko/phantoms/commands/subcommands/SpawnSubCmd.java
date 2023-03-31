package me.xginko.phantoms.commands.subcommands;

import me.xginko.phantoms.commands.SubCommand;
import me.xginko.phantoms.utils.PhantomUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnSubCmd extends SubCommand {

    @Override
    public String getName() {
        return "spawn";
    }

    @Override
    public String getDescription() {
        return "Spawn phantoms on a player";
    }

    @Override
    public String getSyntax() {
        return "/phantoms spawn <player> <amount>";
    }

    @Override
    public void perform(@NotNull CommandSender sender, String[] args) {
        if (sender.hasPermission("phantoms.cmd.spawn")) {
            if (args.length >= 2) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(Component.text(ChatColor.RED + "Player is not online or does not exist."));
                    return;
                }
                if (args.length > 2) {
                    try {
                        int amount = Integer.parseInt(args[2]);
                        PhantomUtil.spawnPhantomOn(target, amount);
                        sender.sendMessage(Component.text(ChatColor.GREEN + "Spawned " + amount + " phantoms on player " + target.getName()));
                    } catch (NumberFormatException e) {
                        sender.sendMessage(Component.text(ChatColor.RED + "You need to specify a valid amount."));
                    }
                } else {
                    PhantomUtil.spawnPhantomOn(target, 1);
                    sender.sendMessage(Component.text(ChatColor.GREEN + "Spawned one phantom on player " + target.getName()));
                }
            } else {
                sender.sendMessage(Component.text(ChatColor.RED + "You need to at least specify a player."));
            }
        } else {
            sender.sendMessage(Component.text(ChatColor.RED + "You don't have permission to use this command."));
        }
    }
}
