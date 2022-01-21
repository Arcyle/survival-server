package de.arcyle.survival_server.features.homes.commands;

import de.arcyle.survival_server.Main;
import de.arcyle.survival_server.core.CommandSyntax;
import de.arcyle.survival_server.core.FeatureCommand;
import de.arcyle.survival_server.features.homes.Home;
import de.arcyle.survival_server.features.homes.HomesFeature;
import de.arcyle.survival_server.features.homes.HomesManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.CommandSender;
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

            var textComponent = new TextComponent(main.prefix + "§8- §e" + name + " §8[§7" + id + "§8]");
            textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/home " + id));
            textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§aClick to teleport §8(§b"
                    + location.getBlockX() + " §7/ §b" + location.getBlockY() + " §7/ §b" + location.getBlockZ() + "§8)")));

            player.spigot().sendMessage(textComponent);
        });
    }

}
