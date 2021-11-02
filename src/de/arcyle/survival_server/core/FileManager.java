package de.arcyle.survival_server.core;

import de.arcyle.survival_server.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class FileManager {

    private final Main main = Main.getInstance();

    private File file;
    private YamlConfiguration config;

    public void load() {
        var dataFolder = main.getDataFolder();
        if (!dataFolder.exists()) dataFolder.mkdir();

        file = new File(dataFolder, getFullFileName());

        try {
            if (!file.exists())
                file.createNewFile();
        } catch (IOException ignored) {
            Bukkit.getConsoleSender().sendMessage(main.prefix + "§cFailed to load " + getFullFileName());
            return;
        }

        config = YamlConfiguration.loadConfiguration(file);

        readConfig();
    }

    public void save() {
        try {
            writeConfig();

            config.save(file);
        } catch (IOException ignored) {
            Bukkit.getConsoleSender().sendMessage(main.prefix + "§cFailed to save " + getFullFileName());
        }
    }

    private String getFullFileName() {
        return getFileName() + ".yml";
    }

    protected abstract String getFileName();

    protected void readConfig() {}
    protected void writeConfig() {}

    public YamlConfiguration getConfig() {
        return config;
    }

}
