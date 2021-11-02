package de.arcyle.survival_server;

import de.arcyle.survival_server.core.Feature;
import de.arcyle.survival_server.core.FeatureCommand;
import de.arcyle.survival_server.features.homes.HomesFeature;
import de.arcyle.survival_server.features.travel.TravelFeature;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class FeatureLoader {

    private final Main main = Main.getInstance();

    private Constructor<PluginCommand> pluginCommandConstructor;
    private CommandMap commandMap;

    private final Feature[] features = new Feature[] { new TravelFeature(), new HomesFeature() };

    public FeatureLoader() {
        try {
            pluginCommandConstructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            pluginCommandConstructor.setAccessible(true);

            var commandMapField = SimplePluginManager.class.getDeclaredField("commandMap");
            commandMapField.setAccessible(true);

            commandMap = (CommandMap) commandMapField.get(Bukkit.getPluginManager());
        } catch (SecurityException
                | IllegalArgumentException
                | IllegalAccessException
                | NoSuchMethodException
                | NoSuchFieldException ignored) {
        }
    }

    void loadFeatures() {
        for (var feature : features) {
            feature.load(this);
        }
    }

    void unloadFeatures() {
        for (var feature : features) {
            feature.unload();
        }
    }

    public PluginCommand registerCommand(FeatureCommand featureCommand) {
        try {
            var command = pluginCommandConstructor.newInstance(featureCommand.getName(), main);
            command.setAliases(Arrays.asList(featureCommand.getAliases()));

            commandMap.register(main.getDescription().getName(), command);

            return command;
        } catch (SecurityException
                | IllegalArgumentException
                | IllegalAccessException
                | InstantiationException
                | InvocationTargetException ignored) {
            return null;
        }
    }

}
