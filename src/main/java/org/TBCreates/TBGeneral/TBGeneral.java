package org.TBCreates.TBGeneral;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.TBCreates.TBGeneral.commands.*;
import org.TBCreates.TBGeneral.commands.admin.ForceGiveBookCommand;
import org.TBCreates.TBGeneral.commands.admin.GameModeCommand;
import org.TBCreates.TBGeneral.commands.admin.fly;
import org.TBCreates.TBGeneral.commands.menu.Menu;
import org.TBCreates.TBGeneral.commands.menu.OpenSelectorMenuCommand;

import org.TBCreates.TBGeneral.commands.message.MsgCommand;
import org.TBCreates.TBGeneral.commands.message.ReplyCommand;
import org.TBCreates.TBGeneral.commands.admin.AdminVanishCommand;
import org.TBCreates.TBGeneral.handlers.PlayerHandler;
import org.TBCreates.TBGeneral.handlers.TorchHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStreamReader;
import java.io.Reader;

public final class TBGeneral extends JavaPlugin implements Listener {

    // Declare the instance variable for the prefix
    private String prefix;

    @Override
    public void onEnable() {
        // Save the default config if it doesn't exist
        saveDefaultConfig();

        // Load the prefix from the config file before using it
        loadPrefix();

        // Example: Logging with the prefix
        Bukkit.getLogger().info(ChatColor.RED + "-------------------------------------------");
        Bukkit.getLogger().info(prefix + ChatColor.RED +" Plugin Successfully enabled!");
        Bukkit.getLogger().info(ChatColor.RED + "-------------------------------------------");

        // Register commands and listeners
        registerCommands();
        getServer().getPluginManager().registerEvents(this, this);  // Register the event listeners

        // Initialize handlers
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

    // Load the prefix from the config and apply color codes
    public void loadPrefix() {
        // Load the prefix from the config (with a default value if it's not set)
        this.prefix = getConfig().getString("prefix", "&7[TBGeneral] ");
        // Apply color codes to the prefix
        this.prefix = ChatColor.translateAlternateColorCodes('&', prefix);
    }

    // Register commands
    private void registerCommands() {
        getCommand("fly").setExecutor(new fly(this));
        getCommand("menu").setExecutor(new Menu(this)); // Make sure Menu is the correct executor
        getCommand("givebook").setExecutor(new ForceGiveBookCommand(this));
        getCommand("tbgeneral").setExecutor(new TBGeneralCommand(this));
        getCommand("tbgeneral").setTabCompleter(new TBGeneralCommand(this));

        // Additional Game Mode and Other Commands
        Menu adminMenu = new Menu(this);
        OpenSelectorMenuCommand openSelectorMenuCommand = new OpenSelectorMenuCommand(this, adminMenu);
        getCommand("openselectormenu").setExecutor(openSelectorMenuCommand);

        getCommand("gmc").setExecutor(new GameModeCommand(this));
        getCommand("gms").setExecutor(new GameModeCommand(this));
        getCommand("gma").setExecutor(new GameModeCommand(this));
        getCommand("gmsp").setExecutor(new GameModeCommand(this));
        getCommand("heal").setExecutor(new GameModeCommand(this)); // Register heal command
        getCommand("bring").setExecutor(new GameModeCommand(this));
        getCommand("goto").setExecutor(new GameModeCommand(this));

        this.getCommand("msg").setExecutor(new MsgCommand(this));
        this.getCommand("reply").setExecutor(new ReplyCommand(this));


        getCommand("vanish").setExecutor(new AdminVanishCommand(this));
        getServer().getPluginManager().registerEvents(this, this);
    }

    // Custom join message
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        // Set custom join message
        event.setJoinMessage(getPrefix() + " Welcome " + player.getName() + " to the server!");

        Player p = event.getPlayer();
        AdminVanishCommand.vanished.forEach(p::hidePlayer);
    };


    // Load advancement paper data (example for loading JSON)
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
