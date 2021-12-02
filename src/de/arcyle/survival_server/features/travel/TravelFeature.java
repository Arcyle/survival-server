package de.arcyle.survival_server.features.travel;

import de.arcyle.survival_server.core.Feature;
import de.arcyle.survival_server.core.FeatureCommand;
import de.arcyle.survival_server.core.FileManager;
import de.arcyle.survival_server.features.travel.commands.TravelCommand;
import org.bukkit.event.Listener;

public class TravelFeature extends Feature {

    public FileManager[] initFileManagers() { return new FileManager[] { }; }

    public FeatureCommand[] initCommands() {
        return new FeatureCommand[] { new TravelCommand() };
    }

    public Listener[] initEventHandlers() {
        return new Listener[] {};
    }

}
