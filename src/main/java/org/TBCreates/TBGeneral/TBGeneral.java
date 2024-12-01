package org.TBCreates.TBGeneral;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.TBCreates.TBGeneral.Listeners.CacheUnloadListener;
import org.TBCreates.TBGeneral.Listeners.TrophiesMenuListener;
import org.TBCreates.TBGeneral.commands.*;
import org.TBCreates.TBGeneral.data.Cache;
import org.TBCreates.TBGeneral.data.SQLite;
import org.TBCreates.TBGeneral.handlers.AdvancementListener;
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
import org.checkerframework.checker.units.qual.C;

import java.io.InputStreamReader;
import java.io.Reader;

public final class TBGeneral extends JavaPlugin implements Listener {

    // Declare the instance variable for the prefix
    private String prefix;
    private SQLite sqlite;
    private Cache dbCache;

    public SQLite sql()
    {
        return sqlite;
    }

    public Cache cache()
    {
        return dbCache;
    }


    @Override
    public void onEnable() {
        // Save default config if it doesn't exist
        saveDefaultConfig();

        // Load the prefix from the config
        loadPrefix();

        // Load DB
        sqlite = new SQLite(this);
        sqlite.create();
        dbCache = new Cache(this);

        // Example: Logging with the prefix
        Bukkit.getLogger().info("-------------------------------------");
        Bukkit.getLogger().info(prefix + " Plugin enabled!");
        Bukkit.getLogger().info("-------------------------------------");

        // Register commands and listeners
        registerCommands();
        getServer().getPluginManager().registerEvents(this, this);  // Register the event listeners

        // Initialize handlers
        new TorchHandler(this);
        new PlayerHandler(this);
        getServer().getPluginManager().registerEvents(new CacheUnloadListener(this), this);
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

    // Load the prefix from the config and apply color codes
    public void loadPrefix() {
        this.prefix = getConfig().getString("prefix", "&7[TBGeneral] "); // Default prefix if not set
        this.prefix = ChatColor.translateAlternateColorCodes('&', prefix);  // Apply color codes
    }

    // Register commands
    private void registerCommands() {
        getCommand("fly").setExecutor(new fly(this));
        getCommand("menu").setExecutor(new Menu(this));
        getCommand("givebook").setExecutor(new ForceGiveBookCommand(this));
        getCommand("tbgeneral").setExecutor(new TBGeneralCommand(this));
        getCommand("tbgeneral").setTabCompleter(new TBGeneralCommand(this));
        getServer().getPluginManager().registerEvents(new AdvancementListener(this), this);
        // Additional Game Mode and Other Commands
        getServer().getPluginManager().registerEvents(new TrophiesMenuListener(new TrophiesMenuCommand(this)), this);

        getCommand("trophiesmenu").setExecutor(new TrophiesMenuCommand(this));

        Menu adminMenu = new Menu(this); // Ensure you have an appropriate constructor for Menu
        TrophiesMenuCommand trophiesMenuCommand = new TrophiesMenuCommand(this); // Ensure you have an appropriate constructor for Menu
        OpenSelectorMenuCommand openSelectorMenuCommand = new OpenSelectorMenuCommand(this, adminMenu, trophiesMenuCommand);
        getCommand("openselectormenu").setExecutor(openSelectorMenuCommand);

        getServer().getPluginManager().registerEvents(new AdvancementListener(this), this);

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

    private void loadAdvancementPaperData() {
        try {
            // Load the JSON file from the plugin's resources
            Reader reader = new InputStreamReader(getResource("advancementpaper.json"));

            if (reader != null) {
                // Parse the JSON file using Gson
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(reader, JsonObject.class);

                // Example: Print out the data in the JSON
                getLogger().info("Loaded advancement data: " + json.toString());
            } else {
                getLogger().warning("advancementpaper.json not found in resources.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            getLogger().warning("Error loading advancementpaper.json.");
        }
    }

    // Custom leave message
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        // Set custom quit message
        event.setQuitMessage(getPrefix() + " Goodbye " + player.getName() + "!");
    }
}
