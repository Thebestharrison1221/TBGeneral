package org.TBCreates.TBGeneral;

import org.TBCreates.TBGeneral.commands.*;
import org.TBCreates.TBGeneral.handlers.PlayerHandler;
import org.TBCreates.TBGeneral.handlers.TorchHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class TBGeneral extends JavaPlugin implements Listener {

    // Declare the instance variable for the prefix
    private String prefix;

    @Override
    public void onEnable() {
        // Save default config if it doesn't exist
        saveDefaultConfig();
        this.saveDefaultConfig();
        // Get the single prefix from the config
        prefix = getConfig().getString("prefix");  // Default to this if not found

        // Apply color codes to the prefix
        prefix = ChatColor.translateAlternateColorCodes('&', prefix);

        // Example: Logging with the prefix
        Bukkit.getLogger().info("-------------------------------------");
        Bukkit.getLogger().info(prefix + " Plugin enabled!");
        Bukkit.getLogger().info("-------------------------------------");

        // Register commands and events
        getCommand("fly").setExecutor(new fly(this));
        getCommand("vanish").setExecutor(new vanish(this));
        getCommand("menu").setExecutor(new Menu(this));
        getCommand("givebook").setExecutor(new ForceGiveBookCommand(this));
        getCommand("tbgeneral").setExecutor(new TBGeneralCommand(this));
        getCommand("tbgeneral").setTabCompleter(new TBGeneralCommand(this));  // Register tab completer

        getCommand("gmc").setExecutor(new GameModeCommand(this));
        getCommand("gms").setExecutor(new GameModeCommand(this));
        getCommand("gma").setExecutor(new GameModeCommand(this));
        getCommand("gmsp").setExecutor(new GameModeCommand(this));
        getCommand("heal").setExecutor(new GameModeCommand(this)); // Register heal command
        getCommand("bring").setExecutor(new GameModeCommand(this));
        getCommand("goto").setExecutor(new GameModeCommand(this));

        // Register custom handlers or listeners
        new TorchHandler(this);
        new PlayerHandler(this);

        // Register this class as a listener for player join and quit events
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info(prefix + " Stopping");
    }

    // Getter for the prefix
    public String getPrefix() {
        return prefix;
    }

    // Custom join message
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Set custom join message
        event.setJoinMessage(getPrefix() + " Welcome " + player.getName() + " to the server!");
    }

    // Custom leave message
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        // Set custom quit message
        event.setQuitMessage(getPrefix() + " Goodbye " + player.getName() + "!");
    }
}
