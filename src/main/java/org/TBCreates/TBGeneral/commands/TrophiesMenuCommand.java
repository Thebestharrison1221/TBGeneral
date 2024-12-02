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

import java.util.*;

public class TrophiesMenuCommand implements CommandExecutor {

    private final TBGeneral plugin;
    private static final int ITEMS_PER_PAGE = 45; // Maximum items per page
    private final Map<Player, Integer> playerPageMap = new HashMap<>(); // Track player page numbers

    // A map to store advancement keys and their corresponding trophy names
    private static final Map<String, String> advancementKeyToName = new HashMap<>();

    static {
        // Populate the advancement map with keys and their corresponding trophy names
        advancementKeyToName.put("story/stone_tools", "Stone Age");
        advancementKeyToName.put("story/get_upgrade", "Getting an Upgrade");
        advancementKeyToName.put("story/acquire_hardware", "Acquire Hardware");
        advancementKeyToName.put("story/suit_up", "Suit Up");
        advancementKeyToName.put("story/hot_stuff", "Hot Stuff");
        advancementKeyToName.put("story/iron_pick", "Isn't It Iron Pick");
        advancementKeyToName.put("story/not_today_thank_you", "Not Today, Thank You");
        advancementKeyToName.put("story/ice_bucket_challenge", "Ice Bucket Challenge");
        advancementKeyToName.put("story/diamonds", "Diamonds!");
        advancementKeyToName.put("story/we_need_to_go_deeper", "We Need to Go Deeper");
        advancementKeyToName.put("story/cover_me_with_diamonds", "Cover Me With Diamonds");
        advancementKeyToName.put("story/enchanter", "Enchanter");
        advancementKeyToName.put("story/zombie_doctor", "Zombie Doctor");
        advancementKeyToName.put("story/eye_spy", "Eye Spy");
        advancementKeyToName.put("story/the_end", "The End?");
        advancementKeyToName.put("story/free_the_end", "Free the End");
        advancementKeyToName.put("story/the_next_generation", "The Next Generation");
        advancementKeyToName.put("story/remote_getaway", "Remote Getaway");
        advancementKeyToName.put("story/the_end_again", "The End... Again...");
        advancementKeyToName.put("story/you_need_a_mint", "You Need a Mint");
        advancementKeyToName.put("story/adventure", "Adventure");
        advancementKeyToName.put("story/monster_hunter", "Monster Hunter");
        advancementKeyToName.put("story/what_a_deal", "What a Deal!");
        advancementKeyToName.put("story/sweet_dreams", "Sweet Dreams");
        advancementKeyToName.put("story/hero_of_the_village", "Hero of the Village");
        advancementKeyToName.put("story/a_throwaway_joke", "A Throwaway Joke");
        advancementKeyToName.put("story/take_aim", "Take Aim");
        advancementKeyToName.put("story/postmortal", "Postmortal");
        advancementKeyToName.put("story/hired_help", "Hired Help");
        advancementKeyToName.put("story/voluntary_exile", "Voluntary Exile");
        advancementKeyToName.put("story/arbalistic", "Arbalistic");
        advancementKeyToName.put("story/spooky_scary_skeleton", "Spooky Scary Skeleton");
        advancementKeyToName.put("story/into_fire", "Into Fire");
        advancementKeyToName.put("story/local_brewery", "Local Brewery");
        advancementKeyToName.put("story/bring_home_the_beacon", "Bring Home the Beacon");
        advancementKeyToName.put("story/beaconator", "Beaconator");
        advancementKeyToName.put("story/how_did_we_get_here", "How Did We Get Here?");
        advancementKeyToName.put("story/a_balanced_diet", "A Balanced Diet");
        advancementKeyToName.put("story/serious_dedication", "Serious Dedication");
        advancementKeyToName.put("story/hidden_in_the_depths", "Hidden in the Depths");
        advancementKeyToName.put("story/cover_me_in_debris", "Cover Me in Debris");
        advancementKeyToName.put("story/country_lode_take_me_home", "Country Lode, Take Me Home");
        advancementKeyToName.put("story/not_quite_nine_lives", "Not Quite \"Nine\" Lives");
        advancementKeyToName.put("story/who_is_cutting_onions", "Who is Cutting Onions?");
        advancementKeyToName.put("story/oh_shiny", "Oh Shiny");
        advancementKeyToName.put("story/this_boat_has_legs", "This Boat Has Legs");
        advancementKeyToName.put("story/subspace_bubble", "Subspace Bubble");
        advancementKeyToName.put("story/a_seedy_place", "A Seedy Place");
        advancementKeyToName.put("story/best_friends_forever", "Best Friends Forever");
        advancementKeyToName.put("story/fishy_business", "Fishy Business");
        advancementKeyToName.put("story/tactical_fishing", "Tactical Fishing");
        advancementKeyToName.put("story/two_by_two", "Two by Two");
        advancementKeyToName.put("story/parrots_and_the_bats", "Parrots and the Bats");
        advancementKeyToName.put("story/the_cutest_predator", "The Cutest Predator");
        advancementKeyToName.put("story/the_healing_power_of_friendship", "The Healing Power of Friendship!");
        advancementKeyToName.put("story/wax_on", "Wax On");
        advancementKeyToName.put("story/wax_off", "Wax Off");
        advancementKeyToName.put("story/the_power_of_books", "The Power of Books");
        advancementKeyToName.put("minecraft:adventure/avoid_vibration", "Avoid Vibration");
        advancementKeyToName.put("minecraft:adventure/adventuring_time", "Adventuring Time");
        advancementKeyToName.put("recipes/misc/iron_nugget_from_smelting", "Acquire Hardware");
        advancementKeyToName.put("recipes/misc/iron_nugget_from_blasting", "Acquire Hardware");
        advancementKeyToName.put("recipes/misc/iron_nugget_from_smithing", "Getting an Upgrade");
        advancementKeyToName.put("recipes/misc/iron_ingot_from_smelting", "Acquire Hardware");
        advancementKeyToName.put("recipes/misc/iron_ingot_from_blasting", "Acquire Hardware");
        advancementKeyToName.put("recipes/misc/golden_apple_from_golden_block", "Sweet Dreams");
        advancementKeyToName.put("recipes/misc/diamond_sword", "Diamonds!");
        advancementKeyToName.put("recipes/misc/netherite_sword", "Hidden in the Depths");
        advancementKeyToName.put("recipes/misc/diamond_pickaxe", "Diamonds!");
        advancementKeyToName.put("recipes/misc/iron_pickaxe", "Isn't It Iron Pick");
        advancementKeyToName.put("recipes/misc/stone_pickaxe", "Stone Age");
        // Add the rest of the advancements as needed...
    }

    // Define the AdvancementEntry class for storing trophy information
    public static class AdvancementEntry {
        private final String name;
        private final String description;
        private final int id;

        public AdvancementEntry(String name, String description, int id) {
            this.name = name;
            this.description = description;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public int getId() {
            return id;
        }
    }

    // Create a list of advancements with description and ID
    private List<AdvancementEntry> getManualAdvancements() {
        List<AdvancementEntry> advancements = new ArrayList<>();

        // Iterate over the advancement map and create AdvancementEntry objects
        for (Map.Entry<String, String> entry : advancementKeyToName.entrySet()) {
            String name = entry.getValue();
            String description = "Achievement: " + name;  // Placeholder description

            int id = advancements.size() + 1;  // Simple incremental ID based on list index

            // Add the new AdvancementEntry to the list
            advancements.add(new AdvancementEntry(name, description, id));
        }

        return advancements;
    }

    public TrophiesMenuCommand(TBGeneral plugin) {
        this.plugin = plugin;
    }

    // Open the trophies menu for the player, showing a specific page
    public void openTrophiesMenu(Player player, int page) {
        List<AdvancementEntry> advancements = getManualAdvancements();
        int startIndex = (page - 1) * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, advancements.size());

        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Trophies Menu");

        for (int i = startIndex; i < endIndex; i++) {
            AdvancementEntry entry = advancements.get(i);

            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + entry.getName());
            meta.setLore(Collections.singletonList(ChatColor.GRAY + entry.getDescription()));
            meta.setCustomModelData(entry.getId());  // Set custom model data
            item.setItemMeta(meta);

            inventory.setItem(i - startIndex, item);
        }

        player.openInventory(inventory);
        playerPageMap.put(player, page); // Store the current page
    }

    // Command handler
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            int page = 1;  // Default to the first page

            if (args.length > 0) {
                try {
                    page = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Invalid page number.");
                    return false;
                }
            }

            openTrophiesMenu(player, page);
            return true;
        }

        return false;
    }
}
