package de.arcyle.survival_server.features.travel;

import de.arcyle.survival_server.Main;
import de.arcyle.survival_server.core.CommandSyntax;
import de.arcyle.survival_server.core.FeatureCommand;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TravelCommand implements FeatureCommand {

    private final Main main = Main.getInstance();

    public String getName() {
        return "travel";
    }

    public CommandSyntax getSyntax() {
        return new CommandSyntax(
                new CommandSyntax.SubCommand("<X> <Z>", "Shows a path to the entered destination", true, this::travel),
                new CommandSyntax.SubCommand("", "Cancels traveling", true, this::cancel)
        );
    }

    private void travel(CommandSender sender, String[] args) {
        var player = (Player) sender;

        int x;
        int z;

        try {
            x = Integer.parseInt(args[0]);
        } catch (NumberFormatException ignored) {
            sender.sendMessage(main.prefix + main.invalidNumber(args[0]));
            return;
        }

        try {
            z = Integer.parseInt(args[1]);
        } catch (NumberFormatException ignored) {
            sender.sendMessage(main.prefix + main.invalidNumber(args[1]));
            return;
        }

        TravelDestination.start(x, z, player);

        player.sendMessage(main.prefix + "§7Traveling to §8(§e" + x + " §7/ §e" + z + "§8)");
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
    }

    private void cancel(CommandSender sender, String[] args) {
        var player = (Player) sender;

        var cancelled = TravelDestination.cancel(player);

        if (cancelled) {
            player.sendMessage(main.prefix + "§7Cancelled traveling");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
        } else sender.sendMessage(main.prefix + "§cYou don't have an active travel destination");
    }

}
