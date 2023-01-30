package org.millida.dunespiceflight.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.millida.dunespiceflight.DuneSpiceFlightPlugin;

public abstract class AbstractCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            this.execute((Player) sender, args);
            return true;
        }

        return false;
    }

    protected abstract String getCommandName();
    protected abstract void help(CommandSender sender);
    protected abstract void execute(Player player, String[] args);

    public void register() {
        AbstractCommand.registerCommand(this.getCommandName(), this);
    }

    private static void registerCommand(String name, AbstractCommand executor) {
        DuneSpiceFlightPlugin.getInstance().getCommand(name).setExecutor(executor);
    }
}