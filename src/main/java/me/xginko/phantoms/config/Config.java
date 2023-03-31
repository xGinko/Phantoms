package me.xginko.phantoms.config;

import io.github.thatsmusic99.configurationmaster.api.ConfigFile;
import io.github.thatsmusic99.configurationmaster.api.ConfigSection;
import me.xginko.phantoms.Phantoms;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Config {

    private ConfigFile config;
    private final File configFile;
    private final Logger logger;

    public final boolean should_disable_natural_phantoms, should_burn_in_sunlight, custom_max_health_enabled,
                        custom_size_enabled, custom_attack_damage_enabled, should_remove_if_no_player_in_sight;
    public final int spawn_amount, custom_health, min_size, max_size;
    public final double attack_damage;
    public final long spawn_interval_in_ticks, attack_frequency_in_ticks;
    public final HashSet<String> enabledWorlds = new HashSet<>();

    public Config() {
        configFile = new File(Phantoms.getInstance().getDataFolder(), "config.yml");
        logger = Phantoms.getLog();
        createFiles();
        loadConfig();

        // General
        config.addSection("General Settings");
        this.enabledWorlds.addAll(getList("general.enabled-worlds", List.of("world", "world_nether", "world_the_end"), "The worlds in which the plugin will be active."));
        this.should_disable_natural_phantoms = getBoolean("general.disable-natural-phantoms", true, "Recommended to leave on to not spawn too many phantoms.");
        this.spawn_amount = getInt("general.spawn-amount", 3, "How many phantoms to spawn per interval.");
        this.spawn_interval_in_ticks = getInt("general.spawn-interval", 600, "How many seconds to wait before spawning the next set of phantoms.") * 20L;

        // Phantom settings
        config.addSection("Phantom Settings");
        this.attack_frequency_in_ticks = getInt("phantoms.custom-behavior.custom-attack-behavior.frequency-in-seconds", 4, "Delay in seconds until phantom attacks again.") * 20L;
        this.should_remove_if_no_player_in_sight = getBoolean("phantoms.remove-if-no-players-in-target-range", true);
        this.should_burn_in_sunlight = getBoolean("phantoms.burn-in-sunlight", false);
        this.custom_max_health_enabled = getBoolean("phantoms.custom-health.enabled", false);
        this.custom_health = getInt("phantoms.custom-health.health-in-points", 3);
        this.custom_size_enabled = getBoolean("phantoms.custom-size.enabled", false);
        this.min_size = getInt("phantoms.custom-size.min-size", 3);
        this.max_size = getInt("phantoms.custom-size.max-size", 12) + 1;
        this.custom_attack_damage_enabled = getBoolean("phantoms.custom-attack-damage.enabled", false);
        this.attack_damage = getDouble("phantoms.custom-attack-damage.damage-amount-in-hearts", 3.0);
    }

    private void createFiles() {
        try {
            File parent = new File(configFile.getParent());
            if (!parent.exists()) {
                if (!parent.mkdir()) logger.severe("Unable to create plugin config directory.");
            }
            if (!configFile.exists()) {
                configFile.createNewFile(); // Result can be ignored because this method only returns false if the file already exists,
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadConfig() {
        try {
            config = ConfigFile.loadConfig(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            config.save();
        } catch (IOException e) {
            logger.severe("Failed to save config file! - " + e.getLocalizedMessage());
        }
    }

    public boolean getBoolean(String path, boolean def, String comment) {
        config.addDefault(path, def, comment);
        return config.getBoolean(path, def);
    }

    public boolean getBoolean(String path, boolean def) {
        config.addDefault(path, def);
        return config.getBoolean(path, def);
    }

    public String getString(String path, String def, String comment) {
        config.addDefault(path, def, comment);
        return config.getString(path, def);
    }

    public String getString(String path, String def) {
        config.addDefault(path, def);
        return config.getString(path, def);
    }

    public double getDouble(String path, Double def, String comment) {
        config.addDefault(path, def, comment);
        return config.getDouble(path, def);
    }

    public double getDouble(String path, Double def) {
        config.addDefault(path, def);
        return config.getDouble(path, def);
    }

    public int getInt(String path, int def, String comment) {
        config.addDefault(path, def, comment);
        return config.getInteger(path, def);
    }

    public int getInt(String path, int def) {
        config.addDefault(path, def);
        return config.getInteger(path, def);
    }

    public List<String> getList(String path, List<String> def, String comment) {
        config.addDefault(path, def, comment);
        return config.getStringList(path);
    }

    public List<String> getList(String path, List<String> def) {
        config.addDefault(path, def);
        return config.getStringList(path);
    }

    public ConfigSection getConfigSection(String path, Map<String, Object> defaultKeyValue) {
        config.makeSectionLenient(path);
        config.addDefault(path, defaultKeyValue);
        return config.getConfigSection(path);
    }

    public ConfigSection getConfigSection(String path, Map<String, Object> defaultKeyValue, String comment) {
        config.makeSectionLenient(path);
        config.addDefault(path, defaultKeyValue, comment);
        return config.getConfigSection(path);
    }

    public void addComment(String path, String comment) {
        config.addComment(path, comment);
    }
}
