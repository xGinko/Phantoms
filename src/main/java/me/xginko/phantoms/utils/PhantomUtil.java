package me.xginko.phantoms.utils;

import me.xginko.phantoms.Phantoms;
import me.xginko.phantoms.config.Config;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;

public class PhantomUtil {

    public static void spawnPhantomOn(Player player, int amount) {
        Config config = Phantoms.getConfiguration();

        for (int i = 0; i < amount; i++) {
            Phantom phantom = (Phantom) player.getLocation().getWorld().spawnEntity(
                    player.getLocation().add(new Random().nextInt(8), 10, new Random().nextInt(8)),
                    EntityType.PHANTOM,
                    CreatureSpawnEvent.SpawnReason.CUSTOM
            );

            phantom.setShouldBurnInDay(config.should_burn_in_sunlight);
            if (config.custom_max_health_enabled) phantom.setHealth(config.custom_health);
            if (config.custom_size_enabled) phantom.setSize(new Random().nextInt(config.min_size, config.max_size));
            phantom.getPersistentDataContainer().set(Phantoms.getCustomPhantomTag(), PersistentDataType.BYTE, (byte) 1);
            phantom.setTarget(player);
        }
    }

}
