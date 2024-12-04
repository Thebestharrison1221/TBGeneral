package org.TBCreates.TBGeneral;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.TBCreates.TBGeneral.commands.*;
import org.TBCreates.TBGeneral.commands.admin.ForceGiveBookCommand;
import org.TBCreates.TBGeneral.commands.admin.GameModeCommand;
import org.TBCreates.TBGeneral.commands.admin.fly;
import org.TBCreates.TBGeneral.commands.menu.Menu;
import org.TBCreates.TBGeneral.commands.menu.OpenSelectorMenuCommand;

import org.TBCreates.TBGeneral.commands.player.message.MsgCommand;
import org.TBCreates.TBGeneral.commands.player.message.ReplyCommand;
import org.TBCreates.TBGeneral.commands.admin.AdminVanishCommand;
import org.TBCreates.TBGeneral.commands.player.tpcmds.TpAcceptCommand;
import org.TBCreates.TBGeneral.commands.player.tpcmds.TpDenyCommand;
import org.TBCreates.TBGeneral.commands.player.tpcmds.TpaCommand;
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

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.UUID;

public final class TBGeneral extends JavaPlugin implements Listener {

    // Declare the instance variable for the prefix
    private String prefix;

    // HashMap to store teleport requests
    private final HashMap<UUID, UUID> teleportRequests = new HashMap<>();

    @Override
    public void onEnable() {
        // Save the default config if it doesn't exist
        saveDefaultConfig();

        // Load the prefix from the config file before using it
        loadPrefix();

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

    // Getter for teleportRequests
    public HashMap<UUID, UUID> getTeleportRequests() {
        return teleportRequests;
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

        getCommand("tpa").setExecutor(new TpaCommand(this));
        getCommand("tpaccept").setExecutor(new TpAcceptCommand(this));
        getCommand("tpdeny").setExecutor(new TpDenyCommand(this));


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
    }


    // Custom leave message
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        // Set custom quit message
        event.setQuitMessage(getPrefix() + " Goodbye " + player.getName() + "!");
    }
}
