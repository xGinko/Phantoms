package me.xginko.phantoms;

import me.xginko.phantoms.commands.PhantomsCommandModule;
import me.xginko.phantoms.config.Config;
import me.xginko.phantoms.modules.PhantomsModule;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Phantoms extends JavaPlugin {

    private static Phantoms instance;
    private static Logger logger;
    private static Config config;
    private static NamespacedKey custom_phantom_tag;

    @Override
    public void onEnable() {
        instance = this;
        logger = getLogger();

        logger.info("Made by xGinko");

        logger.info("Loading Config");
        reloadConfiguration();

        logger.info("Registering Commands");
        PhantomsCommandModule.reloadCommands();

        logger.info("Registering NamespacedKeys");
        custom_phantom_tag = new NamespacedKey(this, "custom-phantom");
    }

    public static Phantoms getInstance()  {
        return instance;
    }

    public static Config getConfiguration() {
        return config;
    }

    public static Logger getLog() {
        return logger;
    }

    public static NamespacedKey getCustomPhantomTag() {
        return custom_phantom_tag;
    }

    public void reloadPlugin() {
        reloadConfiguration();
        PhantomsCommandModule.reloadCommands();
    }

    private void reloadConfiguration() {
        config = new Config();
        PhantomsModule.reloadModules();
        config.saveConfig();
    }
}
