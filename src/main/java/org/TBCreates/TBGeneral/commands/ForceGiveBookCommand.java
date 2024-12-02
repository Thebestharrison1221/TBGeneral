package org.TBCreates.TBGeneral.commands;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.java.JavaPlugin;

public class ForceGiveBookCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public ForceGiveBookCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("tbgeneral.admin")) {
            sender.sendMessage("§cYou do not have permission to use this command.");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("§cUsage: /givebook <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            sender.sendMessage("§cPlayer not found or not online.");
            return true;
        }

        // Create the book item
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();

        if (bookMeta == null) {
            sender.sendMessage("§cFailed to create book metadata.");
            return true;
        }

        bookMeta.setTitle("TBGeneral Info");
        bookMeta.setAuthor("TheBestHarrison");
        bookMeta.addPage(
                "This plugin is great!\n\n" +
                        "Click the link below to visit:\n\n" +
                        "§n§9https://github.com/Thebestharrison1221/TBGeneral"
        );
        bookMeta.addPage(
                "Commands:\n/gmselect - (/gmsel)\n/fly - (/f)"
        );

        bookMeta.addPage(
                "Permissions:\nTBGeneral.admin"
        );
        book.setItemMeta(bookMeta);

        // Add the book to the player's inventory
        target.getInventory().addItem(book);

        // Optionally remove the persistent data flag
        NamespacedKey key = new NamespacedKey(plugin, "received_tb_general_book");
        PersistentDataContainer dataContainer = target.getPersistentDataContainer();
        dataContainer.remove(key);

        sender.sendMessage("§aGave the TBGeneral Info book to " + target.getName() + ".");
        target.sendMessage("§aYou have been given the TBGeneral Info book!");

        return true;
    }
}
