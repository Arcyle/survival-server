package de.arcyle.survival_server.features.homes.commands;

import de.arcyle.survival_server.Main;
import de.arcyle.survival_server.core.CommandSyntax;
import de.arcyle.survival_server.core.FeatureCommand;
import de.arcyle.survival_server.features.homes.Home;
import de.arcyle.survival_server.features.homes.HomesFeature;
import de.arcyle.survival_server.features.homes.HomesManager;
import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutChat;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Comparator;

public class HomesCommand extends FeatureCommand {

    private final Main main = Main.getInstance();
    private final HomesManager homesManager;

    public HomesCommand(HomesManager homesManager) {
        this.homesManager = homesManager;
    }

    public String getName() { return "homes"; }
    public String[] getAliases() { return new String[] { }; }

    public CommandSyntax getSyntax() {
        return new CommandSyntax(
                new CommandSyntax.SubCommand("", "Lists up all of your homes", true, this::homes)
        );
    }

    private void homes(CommandSender sender, String[] args) {
        var player = (Player) sender;
        var uuid = player.getUniqueId();
        var homes = homesManager.getHomes(uuid);

        if (homes.isEmpty()) {
            player.sendMessage(main.prefix + "§cYou have no homes");
            return;
        }

        var homeCount = homes.size();
        var maxHomes = HomesFeature.MAX_HOMES;
        var homeCountColor = (homeCount < 2f / 3f * maxHomes) ? 'a' : (homeCount < maxHomes ? '6' : 'c');

        player.sendMessage(main.prefix + "§e§lHomes: §8(§" + homeCountColor + homeCount + " §7/ §"
                + homeCountColor + HomesFeature.MAX_HOMES + "§8)");

        homes.stream().sorted(Comparator.comparing(Home::id)).forEach((home) -> {
            var id = home.id();
            var name = home.name();
            var location = home.location();

            var json = "{" +
                            "\"text\":\"" + main.prefix + "§8- §e" + name + " §8[§7" + id + "§8]\"," +
                            "\"clickEvent\": {" +
                                "\"action\":\"run_command\"," +
                                "\"value\":\"/home " + id + "\"" +
                            "}," +
                            "\"hoverEvent\": {" +
                                "\"action\":\"show_text\"," +
                                "\"value\":\"§aClick to teleport §8(§b" +
                                    location.getBlockX() + " §7/ §b" +
                                    location.getBlockY() + " §7/ §b" +
                                    location.getBlockZ() +
                                "§8)\"" +
                            "}" +
                        "}";

            var baseComponent = IChatBaseComponent.ChatSerializer.a(json);
            var packet = new PacketPlayOutChat(baseComponent, ChatMessageType.a, player.getUniqueId());
            var craftPlayer = (CraftPlayer) player;

            craftPlayer.getHandle().b.sendPacket(packet);
        });
    }

}
