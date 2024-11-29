package org.TBCreates.TBGeneral;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class TBGeneral extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("[TBGeneral] Starting");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("[TBGeneral] Stopping");
    }
}
