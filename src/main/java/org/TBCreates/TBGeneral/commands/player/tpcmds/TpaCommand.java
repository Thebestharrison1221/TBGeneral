package org.TBCreates.TBGeneral.commands.player.tpcmds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class TpaCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private final String prefix;  // Store the prefix from the config
    // Map to store the teleport request: requesting player -> target player
    public static final Map<Player, Player> tpaRequests = new HashMap<>();

    public TpaCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        // Load the prefix from the plugin's config
        this.prefix = plugin.getConfig().getString("prefix", "&7[&bTeleport&7] "); // Default value if not set in config
    }

    private String formatMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', prefix + message);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(formatMessage("Only players can use this command."));
            return true;
        }

        Player senderPlayer = (Player) sender;

        // Check if player provides a target to teleport to
        if (args.length != 1) {
            senderPlayer.sendMessage(formatMessage("Usage: /tpa <player>"));
            return true;
        }

        Player targetPlayer = Bukkit.getPlayerExact(args[0]);
        if (targetPlayer == null || !targetPlayer.isOnline()) {
            senderPlayer.sendMessage(formatMessage("Player not found or offline."));
            return true;
        }

        // Prevent teleporting to oneself
        if (senderPlayer.equals(targetPlayer)) {
            senderPlayer.sendMessage(formatMessage("You cannot teleport to yourself."));
            return true;
        }

        // Store the teleport request
        tpaRequests.put(senderPlayer, targetPlayer);

        // Notify the target player
        targetPlayer.sendMessage(formatMessage(senderPlayer.getName() + " wants to teleport to you! Type /tpaccept to accept."));
        senderPlayer.sendMessage(formatMessage("Teleport request sent to " + targetPlayer.getName()));

        // Play sound for the target
        playDing(targetPlayer);

        return true;
    }

    // Accept teleport request
    public void acceptTpa(Player targetPlayer) {
        Player senderPlayer = tpaRequests.get(targetPlayer);  // Get the requesting player

        if (senderPlayer == null) {
            targetPlayer.sendMessage(formatMessage(prefix +"You have no pending teleport requests."));
            return;
        }

        // Start the countdown for teleportation with particles
        int countdown = 5;  // Countdown in seconds
        targetPlayer.sendMessage(formatMessage("Teleporting in " + countdown + " seconds..."));

        new BukkitRunnable() {
            int secondsRemaining = countdown;

            @Override
            public void run() {
                if (secondsRemaining > 0) {
                    // Display particles around the target
                    targetPlayer.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, targetPlayer.getLocation(), 10, 0.5, 0.5, 0.5, 0.1);
                    targetPlayer.sendMessage(formatMessage("Teleporting in " + secondsRemaining + "..."));
                    secondsRemaining--;
                } else {
                    // Apply blindness and teleport
                    targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1)); // Blindness for 5 seconds
                    targetPlayer.teleport(senderPlayer);  // Teleport to the sender

                    targetPlayer.sendMessage(formatMessage("Teleport complete!"));
                    senderPlayer.sendMessage(formatMessage(targetPlayer.getName() + " accepted your teleport request."));

                    // Remove the teleport request after completing the teleportation
                    tpaRequests.remove(targetPlayer);
                    cancel();  // Stop the countdown
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);  // Every second (20 ticks)
    }

    // Command to accept teleport
    public boolean onTpAcceptCommand(CommandSender sender) {
        if (!(sender instanceof Player targetPlayer)) {
            sender.sendMessage(formatMessage("Only players can accept teleport requests!"));
            return false;
        }

        acceptTpa(targetPlayer);
        return true;
    }

    // Play a sound when the target receives a teleport request
    private void playDing(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F);
    }
}
