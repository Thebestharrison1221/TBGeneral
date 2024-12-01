package org.TBCreates.TBGeneral.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AdvancementListener implements Listener {

    private static final Map<String, String> advancementKeyToName = new HashMap<>();
    private static final Set<String> awardedAdvancements = new HashSet<>();

    static {
        // Add manual advancement mappings (key -> name)
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
        advancementKeyToName.put("story/shiny_gear", "Cover Me in Diamonds");

        // Add this new advancement
        advancementKeyToName.put("minecraft:adventure/avoid_vibration", "Avoid Vibration");
        advancementKeyToName.put("minecraft:adventure/adventuring_time", "Adventuring Time");

        // New and other Minecraft advancements that are not in the "story" or "adventure" categories:
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
    }

    @EventHandler
    public void onAdvancement(PlayerAdvancementDoneEvent event) {
        Player player = event.getPlayer();
        String advancementKey = event.getAdvancement().getKey().toString();

        // Debugging - Log the advancement key
        System.out.println("Advancement Key: " + advancementKey);

        // Check if the advancement key is in our map
        if (advancementKeyToName.containsKey(advancementKey)) {
            String advancementName = advancementKeyToName.get(advancementKey);

            // Debugging - Log the name of the completed advancement
            System.out.println(player.getName() + " has completed the advancement: " + advancementName);

            // Check if this advancement has already been given
            if (!hasReceivedTrophy(player, advancementKey)) {
                // Now give the player the trophy
                giveTrophy(player, advancementName);
                markTrophyGiven(player, advancementKey);
            } else {
                System.out.println("Trophy already given for this advancement.");
            }
        } else {
            System.out.println("No matching advancement found for: " + advancementKey);
        }
    }

    private void giveTrophy(Player player, String advancementName) {
        // Create the item to give (paper with the advancement name)
        ItemStack trophy = new ItemStack(Material.PAPER);  // Default trophy (Paper)
        ItemMeta meta = trophy.getItemMeta();

        if (meta != null) {
            // Set the name of the trophy to the advancement name
            meta.setDisplayName(ChatColor.GREEN + advancementName);  // Adding color for clarity

            // Set the lore (description) of the trophy
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Completed the advancement: " + ChatColor.AQUA + advancementName);
            lore.add(ChatColor.YELLOW + "Keep this as a memento!");

            meta.setLore(lore);  // Set the lore
            trophy.setItemMeta(meta);
        }

        // Check if the inventory has space for the item
        if (player.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(trophy);
            System.out.println("Trophy given to " + player.getName());
        } else {
            player.sendMessage(ChatColor.RED + "Your inventory is full! You didn't receive the trophy.");
            System.out.println(player.getName() + "'s inventory is full! No trophy given.");
        }
    }

    // Check if the player has already received the trophy for this advancement
    private boolean hasReceivedTrophy(Player player, String advancementKey) {
        return awardedAdvancements.contains(advancementKey);
    }

    // Mark the advancement as awarded
    private void markTrophyGiven(Player player, String advancementKey) {
        awardedAdvancements.add(advancementKey);
    }
}
