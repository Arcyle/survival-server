package de.arcyle.survival_server.features.homes.commands;

import de.arcyle.survival_server.Main;
import de.arcyle.survival_server.core.CommandSyntax;
import de.arcyle.survival_server.core.FeatureCommand;
import de.arcyle.survival_server.features.homes.HomesManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeleteHomeCommand extends FeatureCommand {

    private final Main main = Main.getInstance();
    private final HomesManager homesManager;

    public DeleteHomeCommand(HomesManager homesManager) {
        this.homesManager = homesManager;
    }

    public String getName() { return "deletehome"; }
    public String[] getAliases() { return new String[] { "delhome", "removehome" }; }

    public CommandSyntax getSyntax() {
        return new CommandSyntax(
                new CommandSyntax.SubCommand("<name>", "Deletes your desired home", true, this::deleteHome)
        );
    }

    private void deleteHome(CommandSender sender, String[] args) {
        var player = (Player) sender;
        var uuid = player.getUniqueId();
        var home = homesManager.getHome(uuid, args[0]);

        if (home == null) {
            player.sendMessage(main.prefix + "§cYou have no home with the name or id " + args[0]);
            return;
        }

        homesManager.deleteHome(uuid, home);
        player.sendMessage(main.prefix + "§7Your home §e" + home.name() + " §7was deleted");
    }

}
