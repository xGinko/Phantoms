package me.xginko.phantoms.modules;

import me.xginko.phantoms.Phantoms;
import org.bukkit.event.HandlerList;

import java.util.HashSet;

public interface PhantomsModule {

    void enable();
    boolean shouldEnable();

    HashSet<PhantomsModule> modules = new HashSet<>();

    static void reloadModules() {
        modules.clear();
        Phantoms plugin = Phantoms.getInstance();
        plugin.getServer().getScheduler().cancelTasks(plugin);
        HandlerList.unregisterAll(plugin);

        // Modules here
        modules.add(new ScheduledPhantomSpawn());
        modules.add(new DisableNaturalPhantomSpawns());
        modules.add(new CustomAttackDamage());
        modules.add(new PhantomBehaviour());

        for (PhantomsModule module : modules) {
            if (module.shouldEnable()) module.enable();
        }
    }
}
