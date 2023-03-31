package me.xginko.phantoms.commands;

import me.xginko.phantoms.Phantoms;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public interface PhantomsCommandModule extends CommandExecutor {

    String label();

    HashSet<PhantomsCommandModule> commands = new HashSet<>();

    static void reloadCommands() {
        commands.clear();

        // Commands here
        commands.add(new PhantomsCmd());

        Phantoms plugin = Phantoms.getInstance();
        CommandMap commandMap = plugin.getServer().getCommandMap();
        for (PhantomsCommandModule command : commands) {
            plugin.getCommand(command.label()).unregister(commandMap);
            plugin.getCommand(command.label()).setExecutor(command);
        }
    }

    @Override
    boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args);
}
