package me.xginko.phantoms.commands;

import me.xginko.phantoms.commands.subcommands.ReloadSubCmd;
import me.xginko.phantoms.commands.subcommands.RemoveSubCmd;
import me.xginko.phantoms.commands.subcommands.SpawnSubCmd;
import me.xginko.phantoms.commands.subcommands.VersionSubCmd;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PhantomsCmd implements PhantomsCommandModule, TabCompleter {

    private final List<SubCommand> subcommands = new ArrayList<>();
    private final List<String> tabCompletes = new ArrayList<>();

    public PhantomsCmd() {
        subcommands.add(new ReloadSubCmd());
        subcommands.add(new VersionSubCmd());
        subcommands.add(new SpawnSubCmd());
        subcommands.add(new RemoveSubCmd());
        for (SubCommand subcommand : subcommands) {
            tabCompletes.add(subcommand.getName());
        }
    }

    @Override
    public String label() {
        return "phantoms";
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender.hasPermission("phantoms.cmd.*")) {
            if (args.length == 1) {
                return tabCompletes;
            }
            if (args.length == 3 && args[0].equalsIgnoreCase("spawn")) {
                return List.of("1", "3", "5", "10", "20", "30");
            }
        }
        return null;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender.hasPermission("phantoms.cmd.*")) {
            if (args.length > 0) {
                boolean cmdExists = false;
                for (SubCommand subcommand : subcommands) {
                    if (args[0].equalsIgnoreCase(subcommand.getName())) {
                        subcommand.perform(sender, args);
                        cmdExists = true;
                    }
                }
                if (!cmdExists) showCommandOverviewTo(sender);
            } else {
                showCommandOverviewTo(sender);
            }
        } else {
            sender.sendMessage(Component.text(ChatColor.RED + "You don't have permission to use this command."));
        }
        return true;
    }

    private void showCommandOverviewTo(CommandSender sender) {
        sender.sendMessage(ChatColor.GRAY+"-----------------------------------------------------");
        sender.sendMessage(ChatColor.WHITE+"Phantoms Commands ");
        sender.sendMessage(ChatColor.GRAY+"-----------------------------------------------------");
        for (SubCommand subcommand : subcommands) {
            sender.sendMessage(
                    ChatColor.WHITE + subcommand.getSyntax()
                            + ChatColor.DARK_GRAY + " - "
                            + ChatColor.GRAY + subcommand.getDescription()
            );
        }
        sender.sendMessage(ChatColor.GRAY+"-----------------------------------------------------");
    }
}
