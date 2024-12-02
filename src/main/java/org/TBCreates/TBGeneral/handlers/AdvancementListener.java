package org.TBCreates.TBGeneral.handlers;

import org.TBCreates.TBGeneral.TBGeneral;
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

    private final HashMap<String, String> advancementKeyToName = new HashMap<>();
    private static final Set<String> awardedAdvancements = new HashSet<>();

    private final TBGeneral plugin;

    public AdvancementListener(TBGeneral plugin)
    {
        this.plugin = plugin;
        // Do this on instance because otherwise it gets garbage collected
        advancementKeyToName.put("minecraft:story/stone_tools", "Stone Age");
        advancementKeyToName.put("minecraft:story/iron_tools", "Getting an Upgrade");
        advancementKeyToName.put("minecraft:story/smelt_iron", "Acquire Hardware");
        advancementKeyToName.put("minecraft:story/obtain_armor", "Suit Up");
        advancementKeyToName.put("minecraft:story/lava_bucket", "Hot Stuff");
        advancementKeyToName.put("minecraft:story/iron_pick", "Isn't It Iron Pick");
        advancementKeyToName.put("minecraft:story/not_today_thank_you", "Not Today, Thank You");
        advancementKeyToName.put("minecraft:story/form_obsidian", "Ice Bucket Challenge");
        advancementKeyToName.put("minecraft:story/diamonds", "Diamonds!");
        advancementKeyToName.put("minecraft:story/we_need_to_go_deeper", "We Need to Go Deeper");
        advancementKeyToName.put("minecraft:story/cover_me_with_diamonds", "Cover Me With Diamonds");
        advancementKeyToName.put("minecraft:story/enchanter", "Enchanter");
        advancementKeyToName.put("minecraft:story/zombie_doctor", "Zombie Doctor");
        advancementKeyToName.put("minecraft:story/eye_spy", "Eye Spy");
        advancementKeyToName.put("minecraft:story/the_end", "The End?");
        advancementKeyToName.put("minecraft:story/free_the_end", "Free the End");
        advancementKeyToName.put("minecraft:story/the_next_generation", "The Next Generation");
        advancementKeyToName.put("minecraft:story/remote_getaway", "Remote Getaway");
        advancementKeyToName.put("minecraft:story/the_end_again", "The End... Again...");
        advancementKeyToName.put("minecraft:story/you_need_a_mint", "You Need a Mint");
        advancementKeyToName.put("minecraft:story/adventure", "Adventure");
        advancementKeyToName.put("minecraft:story/monster_hunter", "Monster Hunter");
        advancementKeyToName.put("minecraft:story/what_a_deal", "What a Deal!");
        advancementKeyToName.put("minecraft:story/sweet_dreams", "Sweet Dreams");
        advancementKeyToName.put("minecraft:story/hero_of_the_village", "Hero of the Village");
        advancementKeyToName.put("minecraft:story/a_throwaway_joke", "A Throwaway Joke");
        advancementKeyToName.put("minecraft:story/take_aim", "Take Aim");
        advancementKeyToName.put("minecraft:story/postmortal", "Postmortal");
        advancementKeyToName.put("minecraft:story/hired_help", "Hired Help");
        advancementKeyToName.put("minecraft:story/voluntary_exile", "Voluntary Exile");
        advancementKeyToName.put("minecraft:story/arbalistic", "Arbalistic");
        advancementKeyToName.put("minecraft:story/spooky_scary_skeleton", "Spooky Scary Skeleton");
        advancementKeyToName.put("minecraft:story/into_fire", "Into Fire");
        advancementKeyToName.put("minecraft:story/local_brewery", "Local Brewery");
        advancementKeyToName.put("minecraft:story/bring_home_the_beacon", "Bring Home the Beacon");
        advancementKeyToName.put("minecraft:story/beaconator", "Beaconator");
        advancementKeyToName.put("minecraft:story/how_did_we_get_here", "How Did We Get Here?");
        advancementKeyToName.put("minecraft:story/a_balanced_diet", "A Balanced Diet");
        advancementKeyToName.put("minecraft:story/serious_dedication", "Serious Dedication");
        advancementKeyToName.put("minecraft:story/hidden_in_the_depths", "Hidden in the Depths");
        advancementKeyToName.put("minecraft:story/cover_me_in_debris", "Cover Me in Debris");
        advancementKeyToName.put("minecraft:story/country_lode_take_me_home", "Country Lode, Take Me Home");
        advancementKeyToName.put("minecraft:story/not_quite_nine_lives", "Not Quite \"Nine\" Lives");
        advancementKeyToName.put("minecraft:story/who_is_cutting_onions", "Who is Cutting Onions?");
        advancementKeyToName.put("minecraft:story/oh_shiny", "Oh Shiny");
        advancementKeyToName.put("minecraft:story/this_boat_has_legs", "This Boat Has Legs");
        advancementKeyToName.put("minecraft:story/subspace_bubble", "Subspace Bubble");
        advancementKeyToName.put("minecraft:story/a_seedy_place", "A Seedy Place");
        advancementKeyToName.put("minecraft:story/best_friends_forever", "Best Friends Forever");
        advancementKeyToName.put("minecraft:story/fishy_business", "Fishy Business");
        advancementKeyToName.put("minecraft:story/tactical_fishing", "Tactical Fishing");
        advancementKeyToName.put("minecraft:story/two_by_two", "Two by Two");
        advancementKeyToName.put("minecraft:story/parrots_and_the_bats", "Parrots and the Bats");
        advancementKeyToName.put("minecraft:story/the_cutest_predator", "The Cutest Predator");
        advancementKeyToName.put("minecraft:story/the_healing_power_of_friendship", "The Healing Power of Friendship!");
        advancementKeyToName.put("minecraft:story/wax_on", "Wax On");
        advancementKeyToName.put("minecraft:story/wax_off", "Wax Off");
        advancementKeyToName.put("minecraft:story/the_power_of_books", "The Power of Books");
        advancementKeyToName.put("minecraft:story/shiny_gear", "Cover Me in Diamonds");

        // Add this new advancement
        advancementKeyToName.put("minecraft:adventure/avoid_vibration", "Avoid Vibration");
        advancementKeyToName.put("minecraft:adventure/adventuring_time", "Adventuring Time");

        // New and other Minecraft advancements that are not in the "story" or "adventure" categories:
        // why tho?
        advancementKeyToName.put("minecraft:recipes/misc/iron_nugget_from_smelting", "Acquire Hardware");
        advancementKeyToName.put("minecraft:recipes/misc/iron_nugget_from_blasting", "Acquire Hardware");
        advancementKeyToName.put("minecraft:recipes/misc/iron_nugget_from_smithing", "Getting an Upgrade");
        advancementKeyToName.put("minecraft:recipes/misc/iron_ingot_from_smelting", "Acquire Hardware");
        advancementKeyToName.put("minecraft:recipes/misc/iron_ingot_from_blasting", "Acquire Hardware");
        advancementKeyToName.put("minecraft:recipes/misc/golden_apple_from_golden_block", "Sweet Dreams");
        advancementKeyToName.put("minecraft:recipes/misc/diamond_sword", "Diamonds!");
        advancementKeyToName.put("minecraft:recipes/misc/netherite_sword", "Hidden in the Depths");
        advancementKeyToName.put("minecraft:recipes/misc/diamond_pickaxe", "Diamonds!");
        advancementKeyToName.put("minecraft:recipes/misc/iron_pickaxe", "Isn't It Iron Pick");
        advancementKeyToName.put("minecraft:recipes/misc/stone_pickaxe", "Stone Age");
    }

    @EventHandler
    public void onAdvancement(PlayerAdvancementDoneEvent event) {
        Player player = event.getPlayer();
        String advancementKey = event.getAdvancement().getKey().toString();

        // Debugging - Log the advancement key
        //System.out.println("Advancement Key: " + advancementKey);
        if(advancementKey.contains("recipes"))
        {
            return;
        }
        // Debugging - Log the name of the completed advancement
        System.out.println(player.getName() + " has completed the advancement: " +  event.getAdvancement().getDisplay().getTitle());

        var trophyList = plugin.cache().getTrophies(player.getUniqueId());

        // Check if this advancement has already been given
        if (!trophyList.contains(advancementKey)) {
            // Now give the player the trophy
            giveTrophy(player, event.getAdvancement().getDisplay().getTitle());
            trophyList.add(advancementKey);
            plugin.cache().setTrophies(player.getUniqueId(), trophyList);
        } else {
            System.out.println("Trophy already given for this advancement.");
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
}
