package org.TBCreates.TBGeneral.commands.player.tpcmds;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TpaCommand implements CommandExecutor {
    private final TpaManager tpaManager;

    public TpaCommand(TpaManager tpaManager) {
        this.tpaManager = tpaManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can execute this command!");
            return true;
        }

        if (label.equalsIgnoreCase("tpa")) {
            if (args.length < 1) {
                player.sendMessage("§cUsage: /" + label + " <player>");
                return true;
            }

            final Player to = Bukkit.getPlayer(args[0]);

            if (to == null) {
                player.sendMessage("§cThis player is not online!");
                return true;
            }

            if (player.getUniqueId().equals(to.getUniqueId())) {
                player.sendMessage("§cYou can't send a TPA request to yourself!");
                return true;
            }

            tpaManager.sendRequest(player, to);
        } else if (label.equalsIgnoreCase("tpaaccept")) {
            tpaManager.acceptRequest(player);
        } else {
            player.sendMessage("§cUnknown command. Use /tpa or /tpaaccept.");
        }

        return true;
    }
}
