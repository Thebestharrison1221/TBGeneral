package org.TBCreates.TBGeneral.commands;

import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class fly implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only plays can run this command");
        }

        Player player = (Player) sender;

        if (player.getAllowFlight()) {
            player.setAllowFlight(false);
            player.sendMessage(Color.YELLOW + "Flying disabled");
        } else {
            player.setAllowFlight(true);
            player.sendMessage(Color.YELLOW + "Flying enabled");
        }

        return  true;
    }
}
