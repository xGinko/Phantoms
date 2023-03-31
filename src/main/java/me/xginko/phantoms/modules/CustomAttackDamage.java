package me.xginko.phantoms.modules;

import me.xginko.phantoms.Phantoms;
import org.bukkit.entity.Phantom;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataType;

public class CustomAttackDamage implements PhantomsModule, Listener {

    protected CustomAttackDamage() {}

    @Override
    public void enable() {
        Phantoms plugin = Phantoms.getInstance();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean shouldEnable() {
        return Phantoms.getConfiguration().custom_attack_damage_enabled;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    private void onPhantomDamagesPlayer(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Phantom phantom) {
            if (phantom.getPersistentDataContainer().has(Phantoms.getCustomPhantomTag(), PersistentDataType.BYTE)) {
                event.setDamage(Phantoms.getConfiguration().attack_damage);
            }
        }
    }
}
