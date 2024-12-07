package org.TBCreates.TBGeneral.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class AdminVanishCommand implements CommandExecutor {

    private final JavaPlugin plugin; // Reference to your main plugin class
    public static final ArrayList<Player> vanished = new ArrayList<>();

    public AdminVanishCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            // Fetch and format the prefix with color codes
            String prefix = ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("prefix", "&7[DefaultPrefix] "));

            if (vanished.contains(p)) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.showPlayer(p);
                }
                // Unvanish
                vanished.remove(p);
                p.sendMessage(prefix + ChatColor.GREEN + " You have unvanished");
            } else {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.hidePlayer(p);
                }
                // Vanish
                vanished.add(p);
                p.sendMessage(prefix + ChatColor.GREEN + " You have vanished");
            }
            return true;
        }
        return false;
    }
}
