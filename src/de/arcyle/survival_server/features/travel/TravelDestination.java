package de.arcyle.survival_server.features.travel;

import de.arcyle.survival_server.Main;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.UUID;

public class TravelDestination {

    private final Main main = Main.getInstance();

    private final int x;
    private final int z;
    private final int accuracy;
    private final UUID uuid;

    private final int scheduleId;

    private static final HashSet<TravelDestination> ACTIVE_TRAVEL_DESTINATIONS = new HashSet<>();

    private static final float DISTANCE_ACCURACY_RATIO = 15f;

    private static final Particle PARTICLE_EFFECT = Particle.CRIT_MAGIC;
    private static final float START_OFFSET = 1f;
    private static final float PARTICLE_SPACING = .25f;
    private static final float LINE_LENGTH = 8f;
    private static final float LINE_CLIMB = 1.5f;

    private TravelDestination(int x, int z, int accuracy, UUID uuid) {
        this.x = x;
        this.z = z;
        this.accuracy = accuracy;
        this.uuid = uuid;

        ACTIVE_TRAVEL_DESTINATIONS.add(this);

        scheduleId = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, this::showGuide, 0, 1);
    }

    private void showGuide() {
        var player = Bukkit.getPlayer(uuid);
        if (player == null || !player.isOnline()) return;

        var currentLocation = player.getLocation();
        var currentX = currentLocation.getX();
        var currentZ = currentLocation.getZ();

        var distanceX = x - currentX;
        var distanceZ = z - currentZ;
        var distanceSquared = distanceX * distanceX + distanceZ * distanceZ;

        if (distanceSquared <= accuracy * accuracy) {
            onArrive(player);
            return;
        }

        var particleCount = LINE_LENGTH / PARTICLE_SPACING;
        var direction = new Vector(x - currentLocation.getX(), 0f, z - currentLocation.getZ()).normalize();
        var step = direction.clone().multiply(PARTICLE_SPACING).setY(LINE_CLIMB / particleCount);
        var particleLocation = player.getLocation().clone();

        particleLocation.add(direction.clone().multiply(START_OFFSET));

        for (var i = 0; i < particleCount; i++) {
            particleLocation.add(step);
            player.spawnParticle(PARTICLE_EFFECT, particleLocation, 1, 0, 0, 0, 0);
        }

        var distance = Math.sqrt(distanceSquared);
        player.sendTitle(" ", "§7§e" + (int) distance + " §7blocks left to travel " +
                "§8(§e" + (int) distanceX + " §7/ §e" + (int) distanceZ + "§8)", 0, 5, 20);
    }

    public static void start(int x, int z, Player player) {
        var uuid = player.getUniqueId();

        cancel(uuid);

        var playerLocation = player.getLocation();

        var distanceX = x - playerLocation.getBlockX();
        var distanceZ = z - playerLocation.getBlockZ();

        var distanceSquared = distanceX * distanceX + distanceZ * distanceZ;
        var distance = Math.sqrt(distanceSquared);

        var accuracy = (int) Math.max((distance / DISTANCE_ACCURACY_RATIO), 3f);

        new TravelDestination(x, z, accuracy, uuid);
    }

    private void onArrive(Player player) {
        cancel(uuid);

        player.sendMessage(main.prefix + "§aYou have arrived at your destination");
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
    }

    public static boolean cancel(UUID uuid) {
        var cancelled = false;
        var removed = new HashSet<TravelDestination>();

        for (var travelDestination : ACTIVE_TRAVEL_DESTINATIONS) {
            if (!uuid.equals(travelDestination.uuid)) continue;

            Bukkit.getScheduler().cancelTask(travelDestination.scheduleId);
            removed.add(travelDestination);
            cancelled = true;
        }

        ACTIVE_TRAVEL_DESTINATIONS.removeAll(removed);
        return cancelled;
    }

}
