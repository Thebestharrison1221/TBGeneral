package org.TBCreates.TBGeneral.commands;

import org.TBCreates.TBGeneral.TBGeneral;  // Import your main plugin class to access the prefix
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameModeCommand implements CommandExecutor {

    private final TBGeneral plugin;

    // Constructor to get the plugin instance (for accessing the prefix)
    public GameModeCommand(TBGeneral plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // Get the prefix from the plugin
            String prefix = plugin.getPrefix();

            // Check if the player has permission to change game mode (optional)
            if (!player.hasPermission("tbgeneral.admin")) {
                player.sendMessage(prefix + " You do not have permission to change game mode!");
                return false;
            }

            // Check the command name and set the correct game mode
            if (label.equalsIgnoreCase("gmc")) {
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(prefix + " Game mode set to CREATIVE.");
            } else if (label.equalsIgnoreCase("gms")) {
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(prefix + " Game mode set to SURVIVAL.");
            } else if (label.equalsIgnoreCase("gma")) {
                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage(prefix + " Game mode set to ADVENTURE.");
            } else if (label.equalsIgnoreCase("gmsp")) {
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(prefix + " Game mode set to SPECTATOR.");
            } else if (label.equalsIgnoreCase("heal")) {
                // Heal the player (restore full health)
                player.setHealth(player.getMaxHealth());
                player.sendMessage(prefix + " You have been healed!");
            } else if (label.equalsIgnoreCase("bring")) {
                // /bring command: brings the target player to the executor's location
                if (args.length == 1) {
                    Player target = player.getServer().getPlayer(args[0]);
                    if (target != null) {
                        target.teleport(player.getLocation());
                        player.sendMessage(prefix + " You have brought " + target.getName() + " to your location.");
                        target.sendMessage(prefix + " You have been brought to " + player.getName() + "'s location.");
                    } else {
                        player.sendMessage(prefix + " Player not found!");
                    }
                } else {
                    player.sendMessage(prefix + " Usage: /bring <player>");
                }
            } else if (label.equalsIgnoreCase("goto")) {
                // /goto command: teleports the executor to the target player
                if (args.length == 1) {
                    Player target = player.getServer().getPlayer(args[0]);
                    if (target != null) {
                        player.teleport(target.getLocation());
                        player.sendMessage(prefix + " You have been teleported to " + target.getName() + ".");
                    } else {
                        player.sendMessage(prefix + " Player not found!");
                    }
                } else {
                    player.sendMessage(prefix + " Usage: /goto <player>");
                }
            }

            return true;
        }

        return false;
    }
}
