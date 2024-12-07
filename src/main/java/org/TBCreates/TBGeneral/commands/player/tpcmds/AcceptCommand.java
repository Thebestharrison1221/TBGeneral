package org.TBCreates.TBGeneral.commands.player.tpcmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AcceptCommand implements CommandExecutor {
    private final TpaManager tpaManager;

    public AcceptCommand(TpaManager tpaManager) {
        this.tpaManager = tpaManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can execute this command!");
            return true;
        }

        // Check if the player has a pending TPA request
        if (!tpaManager.hasPendingRequest(player)) {
            player.sendMessage("§cYou don't have any pending TPA requests.");
            return true;
        }

        // Accept the TPA request
        tpaManager.acceptRequest(player);
        return true;
    }
}
