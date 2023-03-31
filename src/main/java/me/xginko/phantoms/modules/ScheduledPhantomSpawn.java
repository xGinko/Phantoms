package me.xginko.phantoms.modules;

import me.xginko.phantoms.Phantoms;
import me.xginko.phantoms.config.Config;
import me.xginko.phantoms.utils.PhantomUtil;
import org.bukkit.entity.Player;

public class ScheduledPhantomSpawn implements PhantomsModule {

    private final Phantoms plugin;
    private final Config config;

    protected ScheduledPhantomSpawn() {
        this.plugin = Phantoms.getInstance();
        this.config = Phantoms.getConfiguration();
    }

    @Override
    public void enable() {
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                if (config.enabledWorlds.contains(player.getWorld().getName())) {
                    PhantomUtil.spawnPhantomOn(player, config.spawn_amount);
                }
            }
        }, 20L, config.spawn_interval_in_ticks);
    }

    @Override
    public boolean shouldEnable() {
        return !config.enabledWorlds.isEmpty();
    }
}
