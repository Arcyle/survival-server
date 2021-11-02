package de.arcyle.survival_server.features.homes.commands;

import de.arcyle.survival_server.Main;
import de.arcyle.survival_server.core.CommandSyntax;
import de.arcyle.survival_server.core.FeatureCommand;
import de.arcyle.survival_server.features.homes.HomesManager;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand extends FeatureCommand {

    private final Main main = Main.getInstance();
    private final HomesManager homesManager;

    public HomeCommand(HomesManager homesManager) {
        System.out.println(homesManager);
        this.homesManager = homesManager;
    }

    public String getName() { return "home"; }
    public String[] getAliases() { return new String[] { }; }

    public CommandSyntax getSyntax() {
        return new CommandSyntax(
                new CommandSyntax.SubCommand("<name>", "Teleports you to your desired home", true, this::home)
        );
    }

    private void home(CommandSender sender, String[] args) {
        var player = (Player) sender;
        var uuid = player.getUniqueId();
        var home = homesManager.getHome(uuid, args[0]);

        if (home == null) {
            player.sendMessage(main.prefix + "§cYou have no home with the name or id " + args[0]);
            return;
        }

        player.teleport(home.location());
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
    }

}
