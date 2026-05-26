package dk.prayser.pyrocore.utils;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class Config {
    private final JavaPlugin plugin;
    private FileConfiguration config;
    private File configFile;
    private final String folderName, fileName;

    /**
     * Constructor for Config
     * @param instance JavaPlugin instance
     * @param folderName Folder name
     * @param fileName  File name
     */
    public Config(JavaPlugin instance, String folderName, String fileName) {
        this.plugin = instance;
        this.folderName = folderName;
        this.fileName = fileName;
    }

    /**
     * Constructor for Config
     * @return Config
     */
    public FileConfiguration getConfig() {
        if (this.config == null) reloadConfig();
        return this.config;
    }

    /**
     * Save default config
     */

    public void reloadConfig() {
        if (this.configFile == null) {
            if (this.folderName != null && !this.folderName.isEmpty()) {
                this.configFile = new File(this.plugin.getDataFolder() + File.separator + this.folderName, this.fileName);
            } else {
                this.configFile = new File(this.plugin.getDataFolder(), this.fileName);
            }
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8))) {
            this.config = YamlConfiguration.loadConfiguration(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Save config
     */

    public void saveConfig() {
        if (this.config == null || this.configFile == null) return;
        try {
            getConfig().save(this.configFile);
        } catch (IOException ex) {
            this.plugin.getLogger().log(Level.SEVERE, "Could not save config to" + this.configFile, ex);
        }
    }

    public void checkNonNullValues(ConfigurationSection config) {
        checkNonNullValues(config, "");
    }

    private void checkNonNullValues(@NotNull ConfigurationSection config, String currentPath) {
        config.getKeys(false).forEach(key -> {
            String fullPath = currentPath.isEmpty() ? key : currentPath + "." + key;

            if (config.isConfigurationSection(key)) {
                checkNonNullValues(config.getConfigurationSection(key), fullPath);
            } else {
                Object value = config.get(key);
                logValueInfo(fullPath, value);
            }
        });
    }

    private void logValueInfo(String fullPath, Object value) {
        if (value == null) {
            System.out.println("Null value found in configuration at: " + fullPath);
        }
    }
}