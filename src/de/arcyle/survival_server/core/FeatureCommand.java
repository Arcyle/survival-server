package de.arcyle.survival_server.core;

import de.arcyle.survival_server.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public abstract class FeatureCommand {

    private final Main main = Main.getInstance();

    public void load(PluginCommand command) {
        if (command == null) return;
        command.setExecutor((s, c, l, a) -> executeCommand(s, a));
    }

    private boolean executeCommand(CommandSender sender, String[] passedArgs) {
        for (var subCommand : getSyntax().subCommands()) {
            var expectedArgs = subCommand.usage().isEmpty() ? new String[0] : subCommand.usage().split(" ");

            if (expectedArgs.length != passedArgs.length) continue;

            if (subCommand.playerOnly() && !(sender instanceof Player)) {
                sender.sendMessage(main.prefix + main.playerOnly);
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

        sendUsage(sender);
        return true;
    }

    private void sendUsage(CommandSender sender) {
        sender.sendMessage(main.prefix + "§e§lUsage:");

        var start = main.prefix + "§8- §e/" + getName();

        for (var subCommand : getSyntax().subCommands()) {
            var usage = subCommand.usage();
            var description = subCommand.description();

            sender.sendMessage(start + (usage.isEmpty() ? "" : " " + usage) + " §8- §7" + description);
        }
    }

    public abstract String getName();
    public abstract String[] getAliases();
    public abstract CommandSyntax getSyntax();

}
