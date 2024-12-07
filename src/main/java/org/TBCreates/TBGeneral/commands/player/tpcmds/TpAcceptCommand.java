package org.TBCreates.TBGeneral.commands.player.tpcmds;

import org.TBCreates.TBGeneral.TBGeneral;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TpAcceptCommand implements CommandExecutor {

    private final TBGeneral plugin;

    public TpAcceptCommand(TBGeneral plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player accepter = (Player) sender;

        // Thread-safe handling of teleport requests
        UUID requesterUuid;
        synchronized (plugin.getTeleportRequests()) {
            requesterUuid = plugin.getTeleportRequests().remove(accepter.getUniqueId());
        }

        if (requesterUuid == null) {
            accepter.sendMessage(formatMessage("&cYou have no pending teleport requests."));
            return true;
        }

        Player requester = Bukkit.getPlayer(requesterUuid);
        if (requester == null || !requester.isOnline()) {
            accepter.sendMessage(formatMessage("&cThe player who sent the request is no longer online."));
            Bukkit.getLogger().warning("Teleport request from " + requesterUuid + " to " + accepter.getName() + " could not complete: requester offline.");
            return true;
        }

        // Handle cross-world teleport edge case
        if (!accepter.getWorld().equals(requester.getWorld())) {
            accepter.sendMessage(formatMessage("&cTeleport request canceled: You are in different worlds."));
            requester.sendMessage(formatMessage("&cTeleport request to " + accepter.getName() + " failed: different worlds."));
            return true;
        }

        // Perform the teleport
        requester.teleport(accepter.getLocation());
        accepter.sendMessage(formatMessage("&aTeleport request accepted."));
        requester.sendMessage(formatMessage("&aYour teleport request to " + accepter.getName() + " has been accepted."));
        return true;
    }

    private String formatMessage(String message) {
        String prefix = plugin.getPrefix() != null ? plugin.getPrefix() : "&7[Teleport]";
        return ChatColor.translateAlternateColorCodes('&', prefix + " " + message);
    }
}
