package me.xginko.phantoms.commands.subcommands;

import me.xginko.phantoms.Phantoms;
import me.xginko.phantoms.commands.SubCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Phantom;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class RemoveSubCmd extends SubCommand {
    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getDescription() {
        return "Removes all loaded custom phantoms";
    }

    @Override
    public String getSyntax() {
        return "/phantoms remove";
    }

    @Override
    public void perform(@NotNull CommandSender sender, String[] args) {
        if (sender.hasPermission("phantoms.cmd.remove")) {
            for (World world : Bukkit.getWorlds()) {
                for (LivingEntity livingEntity : world.getLivingEntities()) {
                    if (
                            livingEntity instanceof Phantom phantom
                            && phantom.getPersistentDataContainer().has(Phantoms.getCustomPhantomTag(), PersistentDataType.BYTE)
                    ) {
                        phantom.remove();
                    }
                }
            }
        } else {
            sender.sendMessage(Component.text(
                    ChatColor.RED + "You don't have permission to use this command."
            ));
        }
    }
}
