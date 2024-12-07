package org.TBCreates.TBGeneral.commands.player.tpcmds;

import org.TBCreates.TBGeneral.TBGeneral;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TpDenyCommand implements CommandExecutor {

    private final TBGeneral plugin;

    public TpDenyCommand(TBGeneral plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player denier = (Player) sender;

        // Thread-safe access to teleport requests
        UUID requesterUuid;
        synchronized (plugin.getTeleportRequests()) {
            requesterUuid = plugin.getTeleportRequests().remove(denier.getUniqueId());
        }

        if (requesterUuid == null) {
            denier.sendMessage(formatMessage("&cYou have no pending teleport requests."));
            return true;
        }

        Player requester = Bukkit.getPlayer(requesterUuid);
        if (requester != null && requester.isOnline()) {
            requester.sendMessage(formatMessage("&cYour teleport request to " + denier.getName() + " has been denied."));
        } else {
            Bukkit.getLogger().warning("Teleport request from " + requesterUuid + " could not notify the requester: they are offline.");
        }

        denier.sendMessage(formatMessage("&aTeleport request denied."));
        Bukkit.getLogger().info("Teleport request from " + requesterUuid + " to " + denier.getName() + " was denied.");
        return true;
    }

    private String formatMessage(String message) {
        String prefix = plugin.getPrefix() != null ? plugin.getPrefix() : "&7[Teleport]";
        return ChatColor.translateAlternateColorCodes('&', prefix + " " + message);
    }
}
