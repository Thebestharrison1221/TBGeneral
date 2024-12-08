package org.TBCreates.TBGeneral.commands.player.tpcmds;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.TBCreates.TBGeneral.TBGeneral;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TpaManager {
    private final Map<UUID, TpaRequest> requests = new HashMap<>();

    // Send a TPA request
    public void sendRequest(Player from, Player to) {
        if (from.getUniqueId().equals(to.getUniqueId())) {
            from.sendMessage("§cYou can't send a TPA request to yourself!");
            return;
        }

        if (requests.containsKey(to.getUniqueId())) {
            from.sendMessage("§c" + to.getName() + " already has a pending TPA request.");
            return;
        }

        requests.put(to.getUniqueId(), new TpaRequest(from, to));
        from.sendMessage("§aTPA request has been sent to " + to.getName());
        to.sendMessage("§aYou received a TPA request from " + from.getName() + ". Type /tpaaccept to accept it.");
    }

    // Accept a TPA request
    public void acceptRequest(Player player) {
        final TpaRequest request = requests.get(player.getUniqueId());

        if (request == null) {
            player.sendMessage("§cYou don't have any TPA requests.");
            return;
        }

        final Player teleportTo = request.getFrom();
        if (!teleportTo.isOnline()) {
            player.sendMessage("§cThe player who sent the TPA request is no longer online.");
            requests.remove(player.getUniqueId());
            return;
        }

        player.sendMessage("§aTPA request accepted! Teleporting in 5 seconds...");
        teleportTo.sendMessage("§a" + player.getName() + " has accepted your TPA request. Teleporting in 5 seconds...");

        // Countdown with particles and action bar
        new BukkitRunnable() {
            int countdown = 5;

            @Override
            public void run() {
                if (!player.isOnline() || !teleportTo.isOnline()) {
                    player.sendMessage("§cTeleportation cancelled as one of the players is no longer online.");
                    teleportTo.sendMessage("§cTeleportation cancelled as one of the players is no longer online.");
                    requests.remove(player.getUniqueId());
                    cancel(); // Stop the task
                    return;
                }

                if (countdown > 0) {
                    // Display countdown in action bar
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§eTeleporting in " + countdown + " seconds..."));
                    teleportTo.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§e" + player.getName() + " is teleporting in " + countdown + " seconds..."));

                    // Spawn particles around the player
                    Location loc = player.getLocation();
                    player.getWorld().spawnParticle(Particle.PORTAL, loc, 50, 0.5, 1, 0.5, 0.1);

                    countdown--;
                } else {
                    // Perform teleportation
                    player.teleport(teleportTo.getLocation());
                    player.sendMessage("§aYou have been teleported to " + teleportTo.getName() + "!");
                    teleportTo.sendMessage("§a" + player.getName() + " has been teleported to you!");

                    requests.remove(player.getUniqueId());
                    cancel(); // Stop the task
                }
            }
        }.runTaskTimer(TBGeneral.getInstance(), 0, 20L); // Schedule the task to run every second (20 ticks)
    }

    // Check if a player has a pending TPA request
    public boolean hasPendingRequest(Player player) {
        return requests.containsKey(player.getUniqueId());
    }
}
