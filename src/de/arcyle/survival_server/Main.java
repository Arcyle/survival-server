package de.arcyle.survival_server;

import de.arcyle.survival_server.core.Feature;
import de.arcyle.survival_server.core.FeatureCommand;
import de.arcyle.survival_server.features.travel.TravelFeature;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Main extends JavaPlugin {

    private static Main instance;

    public final String prefix = "§6Survival Server §8| §r";
    public final String playerOnly = "§cYou need to be a player";
    public final String invalidNumber(String s) { return "§c" + s + " is not a valid number"; }

    public Main() {
        instance = this;
    }

    @Override
    public void onEnable() {
        loadFeatures();
    }

    private void loadFeatures() {
        var features = new Feature[] { new TravelFeature() };

        for (var feature : features) {
            loadFeature(feature);
        }
    }

    private void loadFeature(Feature feature) {
        for (var featureCommand : feature.getCommands()) {
            loadCommand(featureCommand);
        }
    }

    private void loadCommand(FeatureCommand featureCommand) {
        var command = getCommand(featureCommand.getName());
        if (command != null) command.setExecutor((s, c, l, a) -> executeCommand(featureCommand, s, a));
    }

    private boolean executeCommand(FeatureCommand featureCommand, CommandSender sender, String[] passedArgs) {
        for (var subCommand : featureCommand.getSyntax().subCommands()) {
            var expectedArgs = subCommand.usage().isEmpty() ? new String[0] : subCommand.usage().split(" ");

            if (expectedArgs.length != passedArgs.length) continue;

            if (subCommand.playerOnly() && !(sender instanceof Player)) {
                sender.sendMessage(prefix + playerOnly);
                return true;
            }

            var args = new ArrayList<String>();

            for (var i = 0; i < passedArgs.length; i++) {
                var currentArg = passedArgs[i];
                var expectedArg = expectedArgs[i];

                if (expectedArg.matches("^<\\w*>$"))
                    args.add(currentArg);
            }

            subCommand.commandExecutor().execute(sender, args.toArray(String[]::new));
            return true;
        }

        sender.sendMessage(prefix + "§e§lUsage:");

        var start = prefix + "§8- §e/" + featureCommand.getName();

        for (var subCommand : featureCommand.getSyntax().subCommands()) {
            var usage = subCommand.usage();
            var description = subCommand.description();

            sender.sendMessage(start + (usage.isEmpty() ? "" : " " + usage) + " §8- §7" + description);
        }

        return true;
    }

    public static Main getInstance() {
        return instance;
    }

}
