package de.arcyle.survival_server;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    public final FeatureLoader featureLoader;

    public final String prefix = "§6Survival Server §8| §r";
    public final String playerOnly = "§cYou need to be a player";
    public final String invalidNumber(String s) { return "§c" + s + " is not a valid number"; }

    public Main() {
        instance = this;

        featureLoader = new FeatureLoader();
    }

    public void onEnable() {
        featureLoader.loadFeatures();
    }

    public void onDisable() {
        featureLoader.unloadFeatures();
    }

    public static Main getInstance() {
        return instance;
    }

}
