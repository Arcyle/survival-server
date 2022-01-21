package de.arcyle.survival_server.features.chat.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatEventListener implements Listener {

    @EventHandler
    public void handleAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        var player = event.getPlayer();
        var message = event.getMessage();

        event.setFormat("§b" + player.getName() + " §8» §7" + message);
    }

}
