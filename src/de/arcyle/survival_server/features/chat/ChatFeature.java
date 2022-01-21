package de.arcyle.survival_server.features.chat;

import de.arcyle.survival_server.core.Feature;
import de.arcyle.survival_server.core.FeatureCommand;
import de.arcyle.survival_server.core.FileManager;
import de.arcyle.survival_server.features.chat.listeners.AsyncPlayerChatEventListener;
import de.arcyle.survival_server.features.chat.listeners.PlayerJoinEventListener;
import de.arcyle.survival_server.features.chat.listeners.PlayerQuitEventListener;
import org.bukkit.event.Listener;

public class ChatFeature extends Feature {

    protected FileManager[] initFileManagers() {
        return new FileManager[] {};
    }

    protected FeatureCommand[] initCommands() {
        return new FeatureCommand[] {};
    }

    protected Listener[] initEventHandlers() {
        return new Listener[] {
                new AsyncPlayerChatEventListener(),
                new PlayerJoinEventListener(),
                new PlayerQuitEventListener()
        };
    }

}
