package me.xginko.phantoms.modules;

import me.xginko.phantoms.Phantoms;
import me.xginko.phantoms.config.Config;
import org.bukkit.entity.Phantom;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class DisableNaturalPhantomSpawns implements PhantomsModule, Listener {

    private final Config config;

    protected DisableNaturalPhantomSpawns() {
        this.config = Phantoms.getConfiguration();
    }

    @Override
    public void enable() {
        Phantoms plugin = Phantoms.getInstance();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean shouldEnable() {
        return config.should_disable_natural_phantoms;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    private void onEntitySpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof Phantom phantom) {
            if (config.enabledWorlds.contains(phantom.getWorld().getName())) {
                if (!phantom.getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
