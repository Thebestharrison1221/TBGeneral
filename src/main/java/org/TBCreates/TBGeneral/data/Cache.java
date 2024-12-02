package org.TBCreates.TBGeneral.data;

import org.TBCreates.TBGeneral.TBGeneral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Cache
{
    public HashMap<UUID, ArrayList<String>> advancementKeys;
    private TBGeneral plugin;
    public Cache(TBGeneral plugin)
    {
        advancementKeys = new HashMap<>();
        this.plugin = plugin;
    }

    public ArrayList<String> getTrophies(UUID player)
    {
        // Lazy load from database
        var tryGet = advancementKeys.get(player);
        if(tryGet == null)
        {
            var record = plugin.sql().getTrophiesRecord(player);
            advancementKeys.put(player, record);
            return record;
        }
        return tryGet;
    }

    public void setTrophies(UUID player, ArrayList<String> trophies)
    {
        plugin.sql().updateTrophiesRecord(player, advancementKeys.get(trophies));
    }

    public void unload(UUID unload)
    {
        // Savefail insurance
        plugin.sql().updateTrophiesRecord(unload, advancementKeys.get(unload));
        // clear uneeded resources
        advancementKeys.remove(unload);
    }
}
