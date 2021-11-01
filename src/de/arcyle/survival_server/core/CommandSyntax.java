package de.arcyle.survival_server.core;

public record CommandSyntax(SubCommand... subCommands) {

    public record SubCommand(String usage, String description, boolean playerOnly, CommandExecutor commandExecutor) {}

}
