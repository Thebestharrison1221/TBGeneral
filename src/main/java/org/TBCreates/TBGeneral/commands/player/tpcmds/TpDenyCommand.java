package org.TBCreates.TBGeneral.commands.player.tpcmds;

import org.TBCreates.TBGeneral.TBGeneral;
import org.bukkit.Bukkit;
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
        UUID requesterUuid = plugin.getTeleportRequests().remove(denier.getUniqueId());

        if (requesterUuid == null) {
            denier.sendMessage("You have no pending teleport requests.");
            return true;
        }

        Player requester = Bukkit.getPlayer(requesterUuid);
        if (requester != null && requester.isOnline()) {
            requester.sendMessage("Your teleport request to " + denier.getName() + " has been denied.");
        }
        denier.sendMessage("Teleport request denied.");
        return true;
    }
}
