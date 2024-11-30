package org.TBCreates.TBGeneral.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class fly implements CommandExecutor {

    private final JavaPlugin plugin;

    // Constructor where the plugin instance is passed in
    public fly(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Ensure the sender is a player
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command");
            return false;
        }

        Player player = (Player) sender;

        // Get the prefix from the config and translate color codes
        String prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix"));

        // Toggle flying state
        if (player.getAllowFlight()) {
            player.setAllowFlight(false);
            player.sendMessage(prefix + "§eFlying disabled");
        } else {
            player.setAllowFlight(true);
            player.sendMessage(prefix + "§eFlying enabled");
        }

        return true;
    }
}
