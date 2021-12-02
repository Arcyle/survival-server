package de.arcyle.survival_server.features.death_counter.events;

import de.arcyle.survival_server.features.death_counter.DeathCounterManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathEventHandler implements Listener {

    private final DeathCounterManager deathCounterManager;

    public PlayerDeathEventHandler(DeathCounterManager deathCounterManager) {
        this.deathCounterManager = deathCounterManager;
    }

    @EventHandler
    public void handlePlayerDeathEvent(PlayerDeathEvent event) {
        var player = event.getEntity();
        var uuid = player.getUniqueId();

        deathCounterManager.addDeath(uuid);
    }

}
