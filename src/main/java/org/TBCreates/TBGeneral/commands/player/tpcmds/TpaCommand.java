package org.TBCreates.TBGeneral.commands.player.tpcmds;

import org.TBCreates.TBGeneral.TBGeneral;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TpaCommand implements CommandExecutor {

    private final TBGeneral plugin;

    public TpaCommand(TBGeneral plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        if (args.length < 1) {
            player.sendMessage(plugin.getPrefix() + " Usage: /tpa <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            player.sendMessage(plugin.getPrefix() + " Player not found or not online.");
            return true;
        }

        if (target.getUniqueId().equals(player.getUniqueId())) {
            player.sendMessage(plugin.getPrefix() + " You cannot send a teleport request to yourself!");
            return true;
        }

        plugin.getTeleportRequests().put(target.getUniqueId(), player.getUniqueId());
        player.sendMessage(plugin.getPrefix() + " Teleport request sent to " + target.getName());
        target.sendMessage(plugin.getPrefix() + player.getName() + " wants to teleport to you. Type /tpaccept to accept or /tpdeny to deny.");
        return true;
    }
}
