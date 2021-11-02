package de.arcyle.survival_server.features.homes;

import de.arcyle.survival_server.core.Feature;
import de.arcyle.survival_server.core.FeatureCommand;
import de.arcyle.survival_server.core.FileManager;
import de.arcyle.survival_server.features.homes.commands.DeleteHomeCommand;
import de.arcyle.survival_server.features.homes.commands.HomeCommand;
import de.arcyle.survival_server.features.homes.commands.HomesCommand;
import de.arcyle.survival_server.features.homes.commands.SetHomeCommand;

public class HomesFeature extends Feature {

    private HomesManager homesManager;

    public static final int MAX_HOMES = 20;

    public FileManager[] initFileManagers() {
        return new FileManager[] { homesManager = new HomesManager() };
    }

    public FeatureCommand[] initCommands() {
        return new FeatureCommand[] {
                new HomeCommand(homesManager),
                new SetHomeCommand(homesManager),
                new DeleteHomeCommand(homesManager),
                new HomesCommand(homesManager)
        };
    }

}
