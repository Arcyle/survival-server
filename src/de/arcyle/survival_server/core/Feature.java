package de.arcyle.survival_server.core;

import de.arcyle.survival_server.FeatureLoader;
import de.arcyle.survival_server.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class Feature {

    private final Main main = Main.getInstance();

    private final FileManager[] fileManagers;
    private final FeatureCommand[] commands;
    private final Listener[] eventHandlers;

    public Feature() {
        fileManagers = initFileManagers();
        commands = initCommands();
        eventHandlers = initEventHandlers();
    }

    public void load(FeatureLoader featureLoader) {
        for (var fileManager : fileManagers)
            fileManager.load();

        for (var command : commands)
            command.load(featureLoader.registerCommand(command));

        var pluginManager = Bukkit.getPluginManager();

        for (var eventHandler : eventHandlers)
            pluginManager.registerEvents(eventHandler, main);
    }

    public void unload() {
        for (var fileManager : fileManagers)
            fileManager.save();
    }

    protected abstract FileManager[] initFileManagers();
    protected abstract FeatureCommand[] initCommands();
    protected abstract Listener[] initEventHandlers();

}
