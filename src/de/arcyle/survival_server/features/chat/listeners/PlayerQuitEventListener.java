package de.arcyle.survival_server.features.chat.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitEventListener implements Listener {

    @EventHandler
    public void handlePlayerQuitEvent(PlayerQuitEvent event) {
        var player = event.getPlayer();
        event.setQuitMessage("§c« §7" + player.getName());
    }

}
