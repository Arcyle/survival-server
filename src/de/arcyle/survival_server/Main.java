package de.arcyle.survival_server;

import de.arcyle.survival_server.core.Feature;
import de.arcyle.survival_server.features.travel.TravelFeature;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        loadFeatures();
    }

    private void loadFeatures() {
        var features = new Feature[] {new TravelFeature()};

        for (var feature : features) {
            for (var featureCommand : feature.getCommands()) {
                var command = getCommand(featureCommand.getName());
                if (command != null) command.setExecutor(featureCommand);
            }
        }
    }

}
