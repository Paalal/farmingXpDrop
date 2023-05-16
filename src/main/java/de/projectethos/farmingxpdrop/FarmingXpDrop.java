package de.projectethos.farmingxpdrop;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class FarmingXpDrop extends JavaPlugin {

    private static CoreProtectAPI coreProtectAPI;

    @Override
    public void onEnable() {
        coreProtectAPI = getCoreProtect();
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
    }

    private CoreProtectAPI getCoreProtect() {
        Plugin plugin = getServer().getPluginManager().getPlugin("CoreProtect");
        if (!(plugin instanceof CoreProtect)) return null;
        CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();

        if (!CoreProtect.isEnabled()) return null;
        if (CoreProtect.APIVersion() < 9) return null;

        return CoreProtect;
    }

    public static CoreProtectAPI getCoreProtectAPI() {
        return coreProtectAPI;
    }
}
