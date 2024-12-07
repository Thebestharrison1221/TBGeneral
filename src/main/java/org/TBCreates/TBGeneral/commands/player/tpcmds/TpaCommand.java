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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TpaCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private final String prefix;
    private final boolean showCountdownMessages;
    public static final Map<Player, Player> tpaRequests = new ConcurrentHashMap<>();

    public TpaCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        this.prefix = plugin.getConfig().getString("prefix", "&7[&bTeleport&7] ");
        this.showCountdownMessages = plugin.getConfig().getBoolean("showCountdownMessages", true);
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

        if (label.equalsIgnoreCase("tpa")) {
            // Handle /tpa
            if (args.length != 1) {
                senderPlayer.sendMessage(formatMessage("Usage: /tpa <player>"));
                return true;
            }

            Player targetPlayer = Bukkit.getPlayerExact(args[0]);
            if (targetPlayer == null || !targetPlayer.isOnline()) {
                senderPlayer.sendMessage(formatMessage("Player not found or offline."));
                return true;
            }

            if (senderPlayer.equals(targetPlayer)) {
                senderPlayer.sendMessage(formatMessage("You cannot teleport to yourself."));
                return true;
            }

            tpaRequests.put(senderPlayer, targetPlayer);
            senderPlayer.sendMessage(formatMessage("Teleport request sent to " + targetPlayer.getName()));
            targetPlayer.sendMessage(formatMessage(senderPlayer.getName() + " wants to teleport to you! Type /tpaccept to accept."));
            playDing(targetPlayer);

            // Add a timeout to remove stale requests
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (tpaRequests.get(senderPlayer) == targetPlayer) {
                        tpaRequests.remove(senderPlayer);
                        senderPlayer.sendMessage(formatMessage("Your teleport request to " + targetPlayer.getName() + " has expired."));
                        targetPlayer.sendMessage(formatMessage(senderPlayer.getName() + "'s teleport request has expired."));
                    }
                }
            }.runTaskLater(plugin, 1200L); // 60 seconds timeout

            return true;

        } else if (label.equalsIgnoreCase("tpaccept")) {
            // Handle /tpaccept
            if (!(sender instanceof Player)) {
                sender.sendMessage(formatMessage("Only players can accept teleport requests!"));
                return false;
            }

            Player targetPlayer = (Player) sender;
            acceptTpa(targetPlayer);
            return true;
        }

        return false;
    }

    // Accept teleport request
    public void acceptTpa(Player targetPlayer) {
        // Find the requesting player based on the targetPlayer
        Player requestingPlayer = null;

        for (Map.Entry<Player, Player> entry : tpaRequests.entrySet()) {
            if (entry.getValue().equals(targetPlayer)) {
                requestingPlayer = entry.getKey();
                break;
            }
        }

        if (requestingPlayer == null) {
            targetPlayer.sendMessage(formatMessage("You have no pending teleport requests."));
            return;
        }

        int countdown = 5; // Countdown in seconds
        targetPlayer.sendMessage(formatMessage("Teleporting in " + countdown + " seconds..."));

        Player finalRequestingPlayer = requestingPlayer; // To use in BukkitRunnable
        new BukkitRunnable() {
            int secondsRemaining = countdown;

            @Override
            public void run() {
                if (secondsRemaining > 0) {
                    if (showCountdownMessages) {
                        targetPlayer.sendMessage(formatMessage("Teleporting in " + secondsRemaining + "..."));
                    }
                    targetPlayer.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, targetPlayer.getLocation(), 10, 0.5, 0.5, 0.5, 0.1);
                    secondsRemaining--;
                } else {
                    targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 1)); // Blindness for 2 seconds
                    targetPlayer.teleport(finalRequestingPlayer);

                    targetPlayer.sendMessage(formatMessage("Teleport complete!"));
                    finalRequestingPlayer.sendMessage(formatMessage(targetPlayer.getName() + " accepted your teleport request."));

                    tpaRequests.remove(finalRequestingPlayer);
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L); // Every second (20 ticks)
    }

    private void playDing(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F);
    }
}
