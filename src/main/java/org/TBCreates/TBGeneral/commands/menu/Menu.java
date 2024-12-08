package org.TBCreates.TBGeneral.commands.menu;

import org.TBCreates.TBGeneral.TBGeneral;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Menu implements Listener, CommandExecutor {

    private final String invName = "Gamemode Selector";
    private final HashMap<UUID, Inventory> customInventories = new HashMap<>();

    public Menu(TBGeneral plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    // Handle inventory clicks
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // Ensure this is a custom inventory by checking the map
        if (!customInventories.containsValue(event.getClickedInventory())) {
            return;
        }

        // Cancel the event to prevent default interactions
        event.setCancelled(true);

        // Ensure the clicked slot is valid
        if (event.getSlot() < 0) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();

        // Handle specific slot actions
        switch (slot) {
            case 10:
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage("§aYour game mode has been set to Creative!");
                break;

            case 12:
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage("§aYour game mode has been set to Survival!");
                break;

            case 14:
                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage("§aYour game mode has been set to Adventure!");
                break;

            case 16:
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage("§aYour game mode has been set to Spectator!");
                break;

            default:
                break; // No action for other slots
        }
    }

    // Handle the command to open the menu
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Ensure the sender is a player
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command.");
            return true;
        }

        Player player = (Player) sender;

        // Create the inventory
        Inventory inv = Bukkit.createInventory(player, 9 * 3, invName);

        // Add items to the inventory
        inv.setItem(10, getItem(new ItemStack(Material.BEDROCK), "&9CREATIVE", "&aClick to switch to Creative mode."));
        inv.setItem(12, getItem(new ItemStack(Material.GRASS_BLOCK), "&9SURVIVAL", "&aClick to switch to Survival mode."));
        inv.setItem(14, getItem(new ItemStack(Material.DIAMOND_SWORD), "&9ADVENTURE", "&aClick to switch to Adventure mode."));
        inv.setItem(16, getItem(new ItemStack(Material.BARRIER), "&9SPECTATOR", "&aClick to switch to Spectator mode."));

        // Store the inventory for validation
        customInventories.put(player.getUniqueId(), inv);

        // Open the inventory for the player
        player.openInventory(inv);

        return true;
    }

    // Utility method to create customized items
    private ItemStack getItem(ItemStack item, String name, String... lore) {
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

            List<String> lores = new ArrayList<>();
            for (String s : lore) {
                lores.add(ChatColor.translateAlternateColorCodes('&', s));
            }
            meta.setLore(lores);
            item.setItemMeta(meta);
        }

        return item;
    }
}
