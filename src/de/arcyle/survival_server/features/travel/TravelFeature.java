package de.arcyle.survival_server.features.travel;

import de.arcyle.survival_server.core.Feature;
import de.arcyle.survival_server.core.FeatureCommand;

public class TravelFeature implements Feature {

    @Override
    public FeatureCommand[] getCommands() {
        return new FeatureCommand[] {new TravelCommand()};
    }

}
