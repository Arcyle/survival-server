package de.arcyle.survival_server.core;

import de.arcyle.survival_server.FeatureLoader;

public abstract class Feature {

    private final FeatureCommand[] commands;
    private final FileManager[] fileManagers;

    public Feature() {
        fileManagers = initFileManagers();
        commands = initCommands();
    }

    public void load(FeatureLoader featureLoader) {
        for (var fileManager : fileManagers)
            fileManager.load();

        for (var featureCommand : commands)
            featureCommand.load(featureLoader.registerCommand(featureCommand));
    }

    public void unload() {
        for (var fileManager : fileManagers)
            fileManager.save();
    }

    protected abstract FeatureCommand[] initCommands();
    protected abstract FileManager[] initFileManagers();

}
