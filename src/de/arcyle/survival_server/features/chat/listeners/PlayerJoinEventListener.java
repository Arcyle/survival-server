package de.arcyle.survival_server.features.chat.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinEventListener implements Listener {

    @EventHandler
    public void handlePlayerJoinEvent(PlayerJoinEvent event) {
        var player = event.getPlayer();
        event.setJoinMessage("§a» §7" + player.getName());
    }

}
