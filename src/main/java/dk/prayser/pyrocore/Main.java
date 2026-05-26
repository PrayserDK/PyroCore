package dk.prayser.pyrocore;

import dk.prayser.pyrocore.hooks.VaultHook;
import dk.prayser.pyrocore.listeners.BlockHitFix;
import dk.prayser.pyrocore.listeners.DurabilityFix;
import dk.prayser.pyrocore.listeners.IronDoorListener;
import dk.prayser.pyrocore.utils.Config;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {
    @Getter
    public static Main instance;
    private static Config config;
    private static FileConfiguration configYML;

    @Override
    public void onEnable() {
        instance = this;
        initialiseConfigs();
        getLogger().info("Initialiser alle funktionerne");
        if (!VaultHook.init()) {
            getLogger().severe("Kunne ikke forbinde til Vault. Deaktiverer plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (configYML.getBoolean("listeners_enabled.iron_door")) {
            getServer().getPluginManager().registerEvents(new IronDoorListener(), this);
        }
        if (configYML.getBoolean("listeners_enabled.block_hit")) {
            getServer().getPluginManager().registerEvents(new BlockHitFix(), this);
        }
        if (configYML.getBoolean("listeners_enabled.dura_fix")) {
            getServer().getPluginManager().registerEvents(new DurabilityFix(), this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void initialiseConfigs() {
        saveDefaultConfig();
        File file = new File(getDataFolder(), "config.yml");
        if (!(new File(getDataFolder(), "config.yml")).exists())saveResource("config.yml", false);
        config = new Config(this, null, "config.yml");
        configYML = config.getConfig();
        config.checkNonNullValues(configYML);
    }
}
