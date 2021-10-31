package de.arcyle.survival_server.features.travel;

import de.arcyle.survival_server.core.FeatureCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class TravelCommand implements FeatureCommand {

    public String getName() {
        return "travel";
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        return false;
    }

}
