package me.xginko.phantoms.commands.subcommands;

import me.xginko.phantoms.Phantoms;
import me.xginko.phantoms.commands.SubCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadSubCmd extends SubCommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reload the plugin configuration";
    }

    @Override
    public String getSyntax() {
        return "/phantoms reload";
    }

    @Override
    public void perform(@NotNull CommandSender sender, String[] args) {
        if (sender.hasPermission("phantoms.cmd.reload")) {
            Phantoms.getInstance().reloadPlugin();
            sender.sendMessage(Component.text(ChatColor.GREEN + "Reload complete."));
        } else {
            sender.sendMessage(Component.text(
                    ChatColor.RED + "You don't have permission to use this command."
            ));
        }
    }
}
