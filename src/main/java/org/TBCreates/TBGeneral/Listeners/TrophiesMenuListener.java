package org.TBCreates.TBGeneral.Listeners;

import org.TBCreates.TBGeneral.commands.TrophiesMenuCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TrophiesMenuListener implements Listener {

    private final TrophiesMenuCommand trophiesMenuCommand;

    public TrophiesMenuListener(TrophiesMenuCommand trophiesMenuCommand) {
        this.trophiesMenuCommand = trophiesMenuCommand;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        // Ensure the clicked item is not null and the inventory is the Trophies Menu
        if (clickedItem == null || clickedItem.getItemMeta() == null || !event.getView().getTitle().equals("Trophies Menu")) {
            return;
        }

        event.setCancelled(true); // Cancel the event to prevent taking the item

        ItemMeta meta = clickedItem.getItemMeta();
        if (meta == null) {
            return;
        }

        // Check if the item has custom model data
        if (meta.hasCustomModelData()) {
            int customModelData = meta.getCustomModelData();

            // Give the player the trophy corresponding to the clicked item
            player.sendMessage(ChatColor.GREEN + "You unlocked the " + ChatColor.GOLD + meta.getDisplayName() + ChatColor.GREEN + " trophy!");

            // Optional: You can give a special trophy item, effects, or some reward
            ItemStack trophyItem = new ItemStack(Material.DIAMOND); // You can change this to any special item
            ItemMeta trophyMeta = trophyItem.getItemMeta();
            if (trophyMeta != null) {
                trophyMeta.setDisplayName(ChatColor.GOLD + "Trophy: " + meta.getDisplayName());
                trophyMeta.setCustomModelData(customModelData); // Match the customModelData to the trophy
                trophyItem.setItemMeta(trophyMeta);
            }
            player.getInventory().addItem(trophyItem); // Give the trophy item to the player
        }

        // Handle navigation actions
        if (meta.getDisplayName().equals(ChatColor.AQUA + "Next Page")) {
            trophiesMenuCommand.openTrophiesMenu(player, 1); // Open the next page
        } else if (meta.getDisplayName().equals(ChatColor.AQUA + "Previous Page")) {
            trophiesMenuCommand.openTrophiesMenu(player, -1); // Open the previous page
        } else if (meta.getDisplayName().equals(ChatColor.AQUA + "Back")) {
            player.sendMessage(ChatColor.YELLOW + "Going back to the main menu...");
            // You can handle the back action (e.g., opening a different menu here)
        }
    }
}
