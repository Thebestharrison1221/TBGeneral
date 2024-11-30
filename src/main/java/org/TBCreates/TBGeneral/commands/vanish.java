package org.TBCreates.TBGeneral.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class vanish implements CommandExecutor {

    private final JavaPlugin plugin;

    public vanish(JavaPlugin plugin) {
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

        // Get the prefix from the config (for message formatting)
        String prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix"));

        // Check if the player already has the "vanished" metadata
        if (player.hasMetadata("vanished")) {
            // Player is already invisible, so remove the invisibility
            player.removeMetadata("vanished", plugin);
            player.setInvisible(false); // Set visible again
            player.setCanPickupItems(true); // Allow item pickup again

            // Remove Night Vision effect
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);

            player.sendMessage(prefix + "§eVanish disabled. You are now visible.");
        } else {
            // Player is not invisible, so apply invisibility
            player.setInvisible(true); // Make invisible to other players
            player.setCanPickupItems(false); // Prevent item pickup while vanished

            // Add metadata to track that the player is invisible
            player.setMetadata("vanished", new FixedMetadataValue(plugin, true));

            // Apply Night Vision effect to allow the player to see in the dark
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 255, false, false));
            player.sendMessage(prefix + "§eVanish enabled. You are now invisible and can see in the dark.");
        }

        return true;
    }
}
