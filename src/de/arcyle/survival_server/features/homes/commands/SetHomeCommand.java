package de.arcyle.survival_server.features.homes.commands;

import de.arcyle.survival_server.Main;
import de.arcyle.survival_server.core.CommandSyntax;
import de.arcyle.survival_server.core.FeatureCommand;
import de.arcyle.survival_server.features.homes.Home;
import de.arcyle.survival_server.features.homes.HomesFeature;
import de.arcyle.survival_server.features.homes.HomesManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHomeCommand extends FeatureCommand {

    private final Main main = Main.getInstance();
    private final HomesManager homesManager;

    public SetHomeCommand(HomesManager homesManager) {
        this.homesManager = homesManager;
    }

    public String getName() { return "sethome"; }
    public String[] getAliases() { return new String[] { }; }

    public CommandSyntax getSyntax() {
        return new CommandSyntax(
                new CommandSyntax.SubCommand("<name>", "Creates a home at your current location", true, this::setHome)
        );
    }

    private void setHome(CommandSender sender, String[] args) {
        var player = (Player) sender;
        var uuid = player.getUniqueId();

        if (homesManager.getHome(uuid, args[0], true) == null && homesManager.getHomes(uuid).size() >= HomesFeature.MAX_HOMES) {
            player.sendMessage(main.prefix + "§cYou may not have more than " + HomesFeature.MAX_HOMES + " homes");
            return;
        }

        homesManager.deleteHome(uuid, args[0], true);
        homesManager.setHome(uuid, new Home(homesManager.nextId(uuid), args[0], player.getLocation()));

        player.sendMessage(main.prefix + "§7The home §e" + args[0] + " §7was set");
    }

}
