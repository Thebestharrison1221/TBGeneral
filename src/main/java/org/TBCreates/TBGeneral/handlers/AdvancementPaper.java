package org.TBCreates.TBGeneral.handlers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.entity.Player;

public class AdvancementPaper {

    // Method to create the advancement paper (book)
    public static ItemStack createAdvancementPaper(AdvancementEntry advancement) {
        // Create a written book item
        ItemStack paper = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) paper.getItemMeta();

        if (meta != null) {
            // Set the title and author for the book
            meta.setTitle("Advancement: " + advancement.getName());
            meta.setAuthor("Minecraft");

            // Add the description as the content of the book
            meta.addPage(advancement.getDescription());

            // Apply the metadata to the paper
            paper.setItemMeta(meta);
        }

        return paper;
    }

    // Method to give the paper (book) to the player
    public static void givePaperToPlayer(Player player, AdvancementEntry advancement) {
        // Create the paper and give it to the player
        ItemStack paper = createAdvancementPaper(advancement);
        player.getInventory().addItem(paper);
    }
}
