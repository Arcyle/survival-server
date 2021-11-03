package de.arcyle.survival_server.core;

import org.bukkit.command.CommandSender;

public record CommandSyntax(SubCommand... subCommands) {

    public record SubCommand(String usage, String description, boolean playerOnly, CommandExecutor commandExecutor) {}

    public interface CommandExecutor {
        void execute(CommandSender sender, String[] args);
    }

}
