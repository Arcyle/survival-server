package de.arcyle.survival_server.features.travel;

import de.arcyle.survival_server.core.Feature;
import de.arcyle.survival_server.core.FeatureCommand;

import java.util.Collection;
import java.util.Set;

public class TravelFeature implements Feature {

    @Override
    public Collection<FeatureCommand> getCommands() {
        return Set.of(new TravelCommand());
    }

}
