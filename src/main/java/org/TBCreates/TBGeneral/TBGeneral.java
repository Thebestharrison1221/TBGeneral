package org.TBCreates.TBGeneral;

import org.TBCreates.TBGeneral.Listeners.TrophiesMenuListener;
import org.TBCreates.TBGeneral.commands.*;
import org.TBCreates.TBGeneral.handlers.PlayerHandler;
import org.TBCreates.TBGeneral.handlers.TorchHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
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

        // Get the single prefix from the config, and set a default if not found
        prefix = getConfig().getString("prefix", "&7[TBGeneral] "); // Fallback to a default prefix if not set
        prefix = ChatColor.translateAlternateColorCodes('&', prefix);  // Apply color codes to the prefix

        // Example: Logging with the prefix
        Bukkit.getLogger().info("-------------------------------------");
        Bukkit.getLogger().info(prefix + " Plugin enabled!");
        Bukkit.getLogger().info("-------------------------------------");

        registerCommands();

        new TorchHandler(this);
        new PlayerHandler(this);

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

    // Register commands
    private void registerCommands() {
        getCommand("fly").setExecutor(new fly(this));
        getCommand("vanish").setExecutor(new vanish(this));
        getCommand("menu").setExecutor(new Menu(this));
        getCommand("givebook").setExecutor(new ForceGiveBookCommand(this));
        getCommand("tbgeneral").setExecutor(new TBGeneralCommand(this));
        getCommand("tbgeneral").setTabCompleter(new TBGeneralCommand(this));

        // Additional Game Mode and Other Commands

        getCommand("trophiesmenu").setExecutor((CommandExecutor) new TrophiesMenuCommand(this));
        getServer().getPluginManager().registerEvents(new TrophiesMenuListener(new TrophiesMenuCommand(this)), this);

        Menu adminMenu = new Menu(this); // Ensure you have an appropriate constructor for Menu
        TrophiesMenuCommand trophiesMenuCommand = new TrophiesMenuCommand(this);

        // Register the OpenSelectorMenuCommand
        OpenSelectorMenuCommand openSelectorMenuCommand = new OpenSelectorMenuCommand(this, adminMenu, trophiesMenuCommand);
        getCommand("openselectormenu").setExecutor(openSelectorMenuCommand);


        getCommand("gmc").setExecutor(new GameModeCommand(this));
        getCommand("gms").setExecutor(new GameModeCommand(this));
        getCommand("gma").setExecutor(new GameModeCommand(this));
        getCommand("gmsp").setExecutor(new GameModeCommand(this));
        getCommand("heal").setExecutor(new GameModeCommand(this)); // Register heal command
        getCommand("bring").setExecutor(new GameModeCommand(this));
        getCommand("goto").setExecutor(new GameModeCommand(this));
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
