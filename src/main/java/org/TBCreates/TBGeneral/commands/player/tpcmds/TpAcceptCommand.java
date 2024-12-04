package org.TBCreates.TBGeneral.commands.player.tpcmds;

import org.TBCreates.TBGeneral.TBGeneral;
import org.bukkit.Bukkit;
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
        UUID requesterUuid = plugin.getTeleportRequests().remove(accepter.getUniqueId());

        if (requesterUuid == null) {
            accepter.sendMessage(plugin.getPrefix() + " You have no pending teleport requests.");
            return true;
        }

        Player requester = Bukkit.getPlayer(requesterUuid);
        if (requester == null || !requester.isOnline()) {
            accepter.sendMessage(plugin.getPrefix() + " The player who sent the request is no longer online.");
            return true;
        }

        requester.teleport(accepter.getLocation());
        accepter.sendMessage(plugin.getPrefix() + " Teleport request accepted.");
        requester.sendMessage(plugin.getPrefix() + " Your teleport request to " + accepter.getName() + " has been accepted.");
        return true;
    }
}
