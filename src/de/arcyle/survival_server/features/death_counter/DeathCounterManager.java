package de.arcyle.survival_server.features.death_counter;

import de.arcyle.survival_server.core.FileManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DeathCounterManager extends FileManager {

    private final Map<UUID, Integer> cachedPlayers = new HashMap<>();

    public void readConfig() {
        if (!getConfig().contains("players") || !getConfig().isConfigurationSection("players")) return;

        var playersSection = getConfig().getConfigurationSection("players");
        if (playersSection == null) return;

        for (var uuidString : playersSection.getKeys(false)) {
            var uuid = UUID.fromString(uuidString);
            var playerDeathCount = playersSection.getInt(uuidString);

            cachedPlayers.put(uuid, playerDeathCount);
        }
    }

    public void writeConfig() {
        getConfig().set("players", null);

        var homesSection = getConfig().createSection("players");

        cachedPlayers.forEach((uuid, playerDeathCount) -> homesSection.set(uuid.toString(), playerDeathCount));
    }

    public void addDeath(UUID uuid) {
        cachedPlayers.put(uuid, getDeathCount(uuid) + 1);
    }

    public int getDeathCount(UUID uuid) {
        return cachedPlayers.getOrDefault(uuid, 0);
    }

    protected String getFileName() {
        return "deathCounter";
    }

}
