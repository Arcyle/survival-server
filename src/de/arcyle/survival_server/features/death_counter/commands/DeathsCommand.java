package de.arcyle.survival_server.features.death_counter.commands;

import de.arcyle.survival_server.Main;
import de.arcyle.survival_server.core.CommandSyntax;
import de.arcyle.survival_server.core.FeatureCommand;
import de.arcyle.survival_server.features.death_counter.DeathCounterManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeathsCommand extends FeatureCommand {

    private final Main main = Main.getInstance();
    private final DeathCounterManager deathCounterManager;

    public DeathsCommand(DeathCounterManager deathCounterManager) {
        this.deathCounterManager = deathCounterManager;
    }

    public String getName() {
        return "deaths";
    }

    public String[] getAliases() {
        return new String[] {};
    }

    public CommandSyntax getSyntax() {
        return new CommandSyntax(
                new CommandSyntax.SubCommand("", "Shows the death count of yourself", true, this::deathsSelf),
                new CommandSyntax.SubCommand("<player>", "Shows the death count of another player", false, this::deathsOther)
        );
    }

    private void deathsSelf(CommandSender sender, String[] args) {
        var player = (Player) sender;
        var deathCount = deathCounterManager.getDeathCount(player.getUniqueId());

        sender.sendMessage(main.prefix + "§7You have §e" + deathCount + " §7deaths");
    }

    private void deathsOther(CommandSender sender, String[] args) {
        var targetName = args[0];
        var target = Bukkit.getPlayer(targetName);

        if (target == null) {
            sender.sendMessage(main.prefix + main.playerNotOnline);
            return;
        }

        var deathCount = deathCounterManager.getDeathCount(target.getUniqueId());
        sender.sendMessage(main.prefix + "§e" + target.getName() + " §7has §e" + deathCount + " §7deaths");
    }

}
