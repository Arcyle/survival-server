package de.arcyle.survival_server.features.death_counter;

import de.arcyle.survival_server.core.Feature;
import de.arcyle.survival_server.core.FeatureCommand;
import de.arcyle.survival_server.core.FileManager;
import de.arcyle.survival_server.features.death_counter.commands.DeathsCommand;
import de.arcyle.survival_server.features.death_counter.events.PlayerDeathEventHandler;
import org.bukkit.event.Listener;

public class DeathCounterFeature extends Feature {

    private DeathCounterManager deathCounterManager;

    protected FileManager[] initFileManagers() {
        return new FileManager[] {
                deathCounterManager = new DeathCounterManager()
        };
    }

    protected FeatureCommand[] initCommands() {
        return new FeatureCommand[] {
                new DeathsCommand(deathCounterManager)
        };
    }

    protected Listener[] initEventHandlers() {
        return new Listener[] {
                new PlayerDeathEventHandler(deathCounterManager)
        };
    }

}
