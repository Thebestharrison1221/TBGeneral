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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TrophiesMenuListener implements Listener {

    private final TrophiesMenuCommand trophiesMenuCommand;

    // Trophy map where custom model data corresponds to trophy names
    private static final Map<Integer, String> trophyMap = new HashMap<>();

    static {
        // Advancements to Trophy mapping with model data and names
        trophyMap.put(1, "Stone Age");                    // story/stone_tools
        trophyMap.put(2, "Getting an Upgrade");           // story/get_upgrade
        trophyMap.put(3, "Acquire Hardware");             // story/acquire_hardware
        trophyMap.put(4, "Suit Up");                      // story/suit_up
        trophyMap.put(5, "Hot Stuff");                    // story/hot_stuff
        trophyMap.put(6, "Isn't It Iron Pick");           // story/iron_pick
        trophyMap.put(7, "Not Today, Thank You");         // story/not_today_thank_you
        trophyMap.put(8, "Ice Bucket Challenge");         // story/ice_bucket_challenge
        trophyMap.put(9, "Diamonds!");                    // story/diamonds
        trophyMap.put(10, "We Need to Go Deeper");        // story/we_need_to_go_deeper
        trophyMap.put(11, "Cover Me With Diamonds");      // story/cover_me_with_diamonds
        trophyMap.put(12, "Enchanter");                   // story/enchanter
        trophyMap.put(13, "Zombie Doctor");               // story/zombie_doctor
        trophyMap.put(14, "Eye Spy");                     // story/eye_spy
        trophyMap.put(15, "The End?");                    // story/the_end
        trophyMap.put(16, "Free the End");                // story/free_the_end
        trophyMap.put(17, "The Next Generation");         // story/the_next_generation
        trophyMap.put(18, "Remote Getaway");              // story/remote_getaway
        trophyMap.put(19, "The End... Again...");         // story/the_end_again
        trophyMap.put(20, "You Need a Mint");             // story/you_need_a_mint
        trophyMap.put(21, "Adventure");                   // story/adventure
        trophyMap.put(22, "Monster Hunter");              // story/monster_hunter
        trophyMap.put(23, "What a Deal!");                // story/what_a_deal
        trophyMap.put(24, "Sweet Dreams");                // story/sweet_dreams
        trophyMap.put(25, "Hero of the Village");         // story/hero_of_the_village
        trophyMap.put(26, "A Throwaway Joke");            // story/a_throwaway_joke
        trophyMap.put(27, "Take Aim");                    // story/take_aim
        trophyMap.put(28, "Postmortal");                  // story/postmortal
        trophyMap.put(29, "Hired Help");                  // story/hired_help
        trophyMap.put(30, "Voluntary Exile");             // story/voluntary_exile
        trophyMap.put(31, "Arbalistic");                  // story/arbalistic
        trophyMap.put(32, "Spooky Scary Skeleton");       // story/spooky_scary_skeleton
        trophyMap.put(33, "Into Fire");                   // story/into_fire
        trophyMap.put(34, "Local Brewery");               // story/local_brewery
        trophyMap.put(35, "Bring Home the Beacon");       // story/bring_home_the_beacon
        trophyMap.put(36, "Beaconator");                  // story/beaconator
        trophyMap.put(37, "How Did We Get Here?");        // story/how_did_we_get_here
        trophyMap.put(38, "A Balanced Diet");             // story/a_balanced_diet
        trophyMap.put(39, "Serious Dedication");          // story/serious_dedication
        trophyMap.put(40, "Hidden in the Depths");        // story/hidden_in_the_depths
        trophyMap.put(41, "Cover Me in Debris");          // story/cover_me_in_debris
        trophyMap.put(42, "Country Lode, Take Me Home");  // story/country_lode_take_me_home
        trophyMap.put(43, "Not Quite \"Nine\" Lives");     // story/not_quite_nine_lives
        trophyMap.put(44, "Who is Cutting Onions?");      // story/who_is_cutting_onions
        trophyMap.put(45, "Oh Shiny");                    // story/oh_shiny
        trophyMap.put(46, "This Boat Has Legs");          // story/this_boat_has_legs
        trophyMap.put(47, "Subspace Bubble");             // story/subspace_bubble
        trophyMap.put(48, "A Seedy Place");               // story/a_seedy_place
        trophyMap.put(49, "Best Friends Forever");        // story/best_friends_forever
        trophyMap.put(50, "Fishy Business");              // story/fishy_business
        trophyMap.put(51, "Tactical Fishing");            // story/tactical_fishing
        trophyMap.put(52, "Two by Two");                  // story/two_by_two
        trophyMap.put(53, "Parrots and the Bats");        // story/parrots_and_the_bats
        trophyMap.put(54, "The Cutest Predator");         // story/the_cutest_predator
        trophyMap.put(55, "The Healing Power of Friendship!"); // story/the_healing_power_of_friendship
        trophyMap.put(56, "Wax On");                      // story/wax_on
        trophyMap.put(57, "Wax Off");                     // story/wax_off
        trophyMap.put(58, "The Power of Books");          // story/the_power_of_books
        trophyMap.put(59, "Avoid Vibration");             // minecraft:adventure/avoid_vibration
        trophyMap.put(60, "Adventuring Time");            // minecraft:adventure/adventuring_time
        trophyMap.put(61, "Acquire Hardware");            // recipes/misc/iron_nugget_from_smelting
        trophyMap.put(62, "Acquire Hardware");            // recipes/misc/iron_nugget_from_blasting
        trophyMap.put(63, "Getting an Upgrade");         // recipes/misc/iron_nugget_from_smithing
        trophyMap.put(64, "Acquire Hardware");            // recipes/misc/iron_ingot_from_smelting
        trophyMap.put(65, "Acquire Hardware");            // recipes/misc/iron_ingot_from_blasting
        trophyMap.put(66, "Sweet Dreams");                // recipes/misc/golden_apple_from_golden_block
        trophyMap.put(67, "Diamonds!");                   // recipes/misc/diamond_sword
        trophyMap.put(68, "Hidden in the Depths");        // recipes/misc/netherite_sword
        trophyMap.put(69, "Diamonds!");                   // recipes/misc/diamond_pickaxe
        trophyMap.put(70, "Isn't It Iron Pick");          // recipes/misc/iron_pickaxe
        trophyMap.put(71, "Stone Age");                   // recipes/misc/stone_pickaxe
    }

    public TrophiesMenuListener(TrophiesMenuCommand trophiesMenuCommand) {
        this.trophiesMenuCommand = trophiesMenuCommand;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null || clickedItem.getItemMeta() == null || !event.getView().getTitle().equals("Trophies Menu")) {
            return;
        }

        event.setCancelled(true);

        ItemMeta meta = clickedItem.getItemMeta();
        if (meta == null || !meta.hasCustomModelData()) {
            return;
        }

        int customModelData = meta.getCustomModelData();

        // Fetch the trophy name using the custom model data
        String trophyName = trophyMap.get(customModelData);

        if (trophyName != null) {
            // Create the trophy item and give it to the player
            ItemStack trophyItem = getTrophyByCustomModelData(customModelData, trophyName);
            player.sendMessage(ChatColor.GREEN + "You unlocked the " + ChatColor.GOLD + trophyName + ChatColor.GREEN + " trophy!");
            player.getInventory().addItem(trophyItem);
        }
    }

    private ItemStack getTrophyByCustomModelData(int customModelData, String trophyName) {
        Material trophyMaterial = Material.PAPER; // You can choose a different material if needed

        ItemStack trophyItem = new ItemStack(trophyMaterial);
        ItemMeta trophyMeta = trophyItem.getItemMeta();

        if (trophyMeta != null) {
            trophyMeta.setDisplayName(ChatColor.GOLD + trophyName);
            trophyMeta.setCustomModelData(customModelData);
            trophyMeta.setLore(Collections.singletonList(ChatColor.GRAY + "A special trophy earned!"));

            trophyItem.setItemMeta(trophyMeta);
        }

        return trophyItem;
    }
}
