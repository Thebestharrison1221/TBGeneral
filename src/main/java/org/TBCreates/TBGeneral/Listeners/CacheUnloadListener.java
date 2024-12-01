package org.TBCreates.TBGeneral.Listeners;

import org.TBCreates.TBGeneral.TBGeneral;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class CacheUnloadListener implements Listener
{
    private TBGeneral plugin;

    public CacheUnloadListener(TBGeneral plugin)
    {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        this.plugin.cache().unload(e.getPlayer().getUniqueId());
    }
}
