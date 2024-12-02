package org.TBCreates.TBGeneral.handlers;

import org.TBCreates.TBGeneral.TBGeneral;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;


public class TorchHandler implements Listener {
    public TorchHandler(TBGeneral plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

//    @EventHandler(priority = EventPriority.LOW)
//    public void onTorchPlace_Low(BlockPlaceEvent event) {
//        if (event.getBlock().getType() == Material.TORCH) {
//            return;
//        }
//        if (!event.getPlayer().hasPermission("TBGeneral.torch.diamond")) {
//            event.getBlock().setType(Material.DIAMOND_BLOCK);
//        }
//    }
//    @EventHandler(priority = EventPriority.NORMAL)
//    public void onTorchPlace_Normal(BlockPlaceEvent event) {
//        Block block = event.getBlock();
//
//        if(block.getType() != Material.TORCH) {
//            return;
//        }
//
//        Bukkit.getLogger().info("A torch was placed");
//    }
//
}
