package de.arcyle.survival_server.features.travel;

import de.arcyle.survival_server.core.Feature;
import de.arcyle.survival_server.core.FeatureCommand;
import de.arcyle.survival_server.core.FileManager;
import de.arcyle.survival_server.features.travel.commands.TravelCommand;

public class TravelFeature extends Feature {

    public FeatureCommand[] initCommands() {
        return new FeatureCommand[] { new TravelCommand() };
    }

    public FileManager[] initFileManagers() { return new FileManager[] { }; }

}
