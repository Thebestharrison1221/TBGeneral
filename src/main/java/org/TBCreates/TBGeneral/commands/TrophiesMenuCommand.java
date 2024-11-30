package org.TBCreates.TBGeneral.commands;

import org.TBCreates.TBGeneral.TBGeneral;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TrophiesMenuCommand implements CommandExecutor {

    private final TBGeneral plugin; // The plugin instance to access its methods
    private static final int ITEMS_PER_PAGE = 45; // Maximum items per page (54 slots, excluding buttons)

    public TrophiesMenuCommand(TBGeneral plugin) {
        this.plugin = plugin;
    }

    // Override execute method to handle the command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            int page = 0; // Default to the first page

            // Handle page argument (if any)
            if (args.length > 0) {
                try {
                    page = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Invalid page number.");
                    return false;
                }
            }

            // Open the trophies menu with the requested page
            openTrophiesMenu(player, page);
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return false;
        }
    }

    // Method to open the trophies menu
    public void openTrophiesMenu(Player player, int page) {
        // Create the inventory for the trophies menu (54 slots)
        Inventory inv = Bukkit.createInventory(player, 54, "Trophies Menu");

        // List of manually defined advancements
        List<AdvancementEntry> advancements = getManualAdvancements();

        int startIndex = page * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, advancements.size());

        // Fill the inventory with advancements
        int slot = 0;
        for (int i = startIndex; i < endIndex; i++) {
            AdvancementEntry entry = advancements.get(i);

            // Create a paper item to represent this advancement
            ItemStack paper = new ItemStack(Material.PAPER);
            ItemMeta meta = paper.getItemMeta();

            if (meta != null) {
                meta.setDisplayName(ChatColor.GOLD + entry.getName()); // Set the name to gold
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + entry.getDescription()); // Add description in gray
                lore.add(ChatColor.GREEN + "Trophy: " + (i + 1)); // Add lore to indicate it's a trophy
                meta.setLore(lore);

                // Set custom model data to differentiate each trophy
                meta.setCustomModelData(i + 1); // You can use the index as custom model data

                paper.setItemMeta(meta);
            }

            inv.setItem(slot++, paper);
        }

        // Add navigation buttons (Next, Previous, Back)
        inv.setItem(45, createNavigationItem(Material.ARROW, "Previous Page", "Go to the previous page"));
        inv.setItem(53, createNavigationItem(Material.ARROW, "Next Page", "Go to the next page"));
        inv.setItem(49, createNavigationItem(Material.BOOK, "Back", "Go back to the main menu"));

        // Open the inventory for the player
        player.openInventory(inv);
    }

    // Method to create a navigation item (e.g., arrows and back button)
    private ItemStack createNavigationItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
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

    // Method to manually create a list of advancements (manually enter each advancement)
    private List<AdvancementEntry> getManualAdvancements() {
        List<AdvancementEntry> advancements = new ArrayList<>();

        // Manually define each advancement
        advancements.add(new AdvancementEntry("Stone Age", "Mine stone with your pickaxe", 1));
        advancements.add(new AdvancementEntry("Getting Wood", "Craft a wooden pickaxe", 2));
        advancements.add(new AdvancementEntry("Time to Farm", "Craft a hoe", 3));
        advancements.add(new AdvancementEntry("The End?", "Enter the End dimension", 4));
        advancements.add(new AdvancementEntry("We Need to Go Deeper", "Enter the Nether dimension", 5));
        advancements.add(new AdvancementEntry("Adventure", "Kill a hostile mob", 6));
        advancements.add(new AdvancementEntry("The Lie", "Cook a cake", 7));
        advancements.add(new AdvancementEntry("Monster Hunter", "Kill a monster", 8));
        advancements.add(new AdvancementEntry("Explorer", "Discover all biomes", 9));
        advancements.add(new AdvancementEntry("Across the Sea", "Travel to another dimension", 10));
        // Add more advancements here as needed

        return advancements;
    }

    // Helper class to represent each advancement
    public static class AdvancementEntry {
        private final String name;
        private final String description;
        private final int customModelData;

        public AdvancementEntry(String name, String description, int customModelData) {
            this.name = name;
            this.description = description;
            this.customModelData = customModelData;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public int getCustomModelData() {
            return customModelData;
        }
    }
}
