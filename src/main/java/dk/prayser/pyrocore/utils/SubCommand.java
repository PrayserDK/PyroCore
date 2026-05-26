package dk.prayser.pyrocore.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class SubCommand {
    public abstract String getName();

    public abstract boolean execute(CommandSender sender, Command command, String label, String[] args);
}
