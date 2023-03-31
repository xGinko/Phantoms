package me.xginko.phantoms.modules;

import me.xginko.phantoms.Phantoms;
import me.xginko.phantoms.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PhantomBehaviour implements PhantomsModule {

    private final Config config;
    private final int simulationDistanceInBlocks;

    protected PhantomBehaviour() {
        this.config = Phantoms.getConfiguration();
        this.simulationDistanceInBlocks = Phantoms.getInstance().getServer().getSimulationDistance() * 16;
    }

    @Override
    public void enable() {
        Phantoms plugin = Phantoms.getInstance();
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, replenishTargets, 20L, Phantoms.getConfiguration().attack_frequency_in_ticks);
    }

    @Override
    public boolean shouldEnable() {
        return !config.enabledWorlds.isEmpty();
    }

    private final Runnable replenishTargets = new Runnable() {
        @Override
        public void run() {
            for (World world : Bukkit.getWorlds()) {
                if (!config.enabledWorlds.contains(world.getName())) continue;

                for (LivingEntity livingEntity : world.getLivingEntities()) {
                    if (
                            livingEntity instanceof Phantom phantom
                            && phantom.getPersistentDataContainer().has(Phantoms.getCustomPhantomTag(), PersistentDataType.BYTE)
                    ) {
                        if (phantom.getTarget() == null) {
                            List<Player> playersInSight = new ArrayList<>();
                            for (Player player : world.getPlayers()) {
                                Location playerLoc = player.getLocation();
                                Location phantomLoc = phantom.getLocation();
                                if (new Point(playerLoc.getBlockX(), playerLoc.getBlockZ()).distance(new Point(phantomLoc.getBlockX(), phantomLoc.getBlockZ())) < simulationDistanceInBlocks) {
                                    playersInSight.add(player);
                                }
                            }
                            if (!playersInSight.isEmpty()) {
                                phantom.setTarget(playersInSight.get(new Random().nextInt(0, playersInSight.size())));
                            } else {
                                if (Phantoms.getConfiguration().should_remove_if_no_player_in_sight) phantom.remove();
                            }
                        }
                    }
                }
            }
        }
    };
}
