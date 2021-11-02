package de.arcyle.survival_server.features.homes;

import de.arcyle.survival_server.core.FileManager;
import org.bukkit.Location;

import java.util.*;

public class HomesManager extends FileManager {

    private final Map<UUID, Set<Home>> cachedHomes = new HashMap<>();

    protected void readConfig() {
        if (!getConfig().contains("homes") || !getConfig().isConfigurationSection("homes")) return;

        var homesSection = getConfig().getConfigurationSection("homes");
        if (homesSection == null) return;

        for (var uuidString : homesSection.getKeys(false)) {
            var uuid = UUID.fromString(uuidString);
            var homeList = new HashSet<Home>();

            var playerHomesSection = homesSection.getConfigurationSection(uuidString);
            if (playerHomesSection == null) continue;

            for (var idString : playerHomesSection.getKeys(false)) {
                int id;

                try {
                    id = Integer.parseInt(idString);
                } catch (NumberFormatException ignored) {
                    continue;
                }

                var homeSection = playerHomesSection.getConfigurationSection(idString);
                if (homeSection == null) continue;

                var name = homeSection.getString("name");
                var location = (Location) homeSection.get("location");

                var home = new Home(id, name, location);
                homeList.add(home);
            }

            cachedHomes.put(uuid, homeList);
        }
    }

    protected void writeConfig() {
        getConfig().set("homes", null);

        var homesSection = getConfig().createSection("homes");

        cachedHomes.forEach((uuid, homeList) -> {
            var uuidString = uuid.toString();
            var playerHomesSection = homesSection.createSection(uuidString);

            homeList.forEach((home) -> {
                var idString = String.valueOf(home.id());
                var homeSection = playerHomesSection.createSection(idString);

                homeSection.set("name", home.name());
                homeSection.set("location", home.location());
            });
        });
    }

    public void setHome(UUID uuid, Home home) {
        getHomes(uuid).add(home);
    }

    public void deleteHome(UUID uuid, String name, boolean nameOnly) {
        deleteHome(uuid, getHome(uuid, name, nameOnly));
    }

    public void deleteHome(UUID uuid, Home home) {
        getHomes(uuid).remove(home);
    }

    public Home getHome(UUID uuid, String name) {
        return getHome(uuid, name, false);
    }

    public Home getHome(UUID uuid, String name, boolean nameOnly) {
        var homes = getHomes(uuid);
        if (homes == null) return null;

        if (!nameOnly) {
            for (var home : homes) {
                if (name.equals(String.valueOf(home.id()))) return home;
            }
        }

        for (var home : homes) {
            if (name.equalsIgnoreCase(home.name())) return home;
        }

        return null;
    }

    public Set<Home> getHomes(UUID uuid) {
        if (!cachedHomes.containsKey(uuid))
            cachedHomes.put(uuid, new HashSet<>());

        return cachedHomes.get(uuid);
    }

    public int nextId(UUID uuid) {
        var id = 1;
        var stream = getHomes(uuid).stream().map(Home::id).sorted();

        for (var possibleId : stream.toList()) {
            if (id != possibleId) break;
            id++;
        }

        return id;
    }

    protected String getFileName() { return "homes"; }

}
