package me.xginko.phantoms.commands.subcommands;

import me.xginko.phantoms.Phantoms;
import me.xginko.phantoms.commands.SubCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class VersionSubCmd extends SubCommand {

    @Override
    public String getName() {
        return "version";
    }

    @Override
    public String getDescription() {
        return "Show the plugin version";
    }

    @Override
    public String getSyntax() {
        return "/phantoms version";
    }

    @Override
    public void perform(@NotNull CommandSender sender, String[] args) {
        if (sender.hasPermission("phantoms.cmd.version")) {
            sender.sendMessage("\n");
            sender.sendMessage(
                    ChatColor.WHITE+"VanillaPlaygroundPhantoms v"+ Phantoms.getInstance().getDescription().getVersion()+
                            ChatColor.WHITE+" by "+ChatColor.AQUA+"xGinko"
            );
            sender.sendMessage("\n");
        } else {
            sender.sendMessage(Component.text(
                    ChatColor.RED + "You don't have permission to use this command."
            ));
        }
    }
}
