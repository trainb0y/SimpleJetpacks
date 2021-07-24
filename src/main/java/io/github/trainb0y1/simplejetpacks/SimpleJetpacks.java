package io.github.trainb0y1.simplejetpacks;

import io.github.trainb0y1.simplejetpacks.commands.KotlinCommandTest;
import io.github.trainb0y1.simplejetpacks.commands.JetpackCommands;
import io.github.trainb0y1.simplejetpacks.commands.JetpackTabComplete;
import io.github.trainb0y1.simplejetpacks.events.*;
import io.github.trainb0y1.simplejetpacks.items.ItemManager;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;


public class SimpleJetpacks extends JavaPlugin {
    private static SimpleJetpacks plugin;
    public static boolean oldMotion;

    @Override
    public void onEnable() {
        plugin = this;
        plugin.saveDefaultConfig();
        Logger logger = plugin.getLogger();

        // Check for updates
        if (plugin.getConfig().getBoolean("check-updates")) {
            new UpdateChecker(plugin, 92562).getVersion(newVersion -> {
                newVersion = newVersion.replace("v", ""); // On spigot I have it listed as v0.4, v0.5 etc.
                String currentVersion = this.getDescription().getVersion();
                if (newVersion.equalsIgnoreCase(currentVersion)) {
                    logger.info("Plugin is up to date!");
                } else {
                    logger.warning("There is a new version available!");
                    logger.warning("Current Version: " + currentVersion);
                    logger.warning("New Version: " + newVersion);
                    logger.warning(("Download the latest version at https://www.spigotmc.org/resources/simplejetpacks.92562/"));
                }
            });
        }

        // Register the stuff we need to register
        ItemManager.createJetpacks(this);

        getServer().getPluginManager().registerEvents(new RefuelEventListener(), this);
        getServer().getPluginManager().registerEvents(new JetpackFlyKickListener(), this);
        getServer().getPluginManager().registerEvents(new JetpackBreakListener(), this);
        getServer().getPluginManager().registerEvents(new JetpackToggleListener(), this);
        getServer().getPluginManager().registerEvents(new JetpackFlyingListener(), this);
        getServer().getPluginManager().registerEvents(new JetpackGlideListener(), this);

        plugin.oldMotion = plugin.getConfig().getBoolean("old-motion");

        getCommand("simplejetpacks").setExecutor(new JetpackCommands());
        getCommand("kotlin").setExecutor(new KotlinCommandTest());
        getCommand("simplejetpacks").setTabCompleter(new JetpackTabComplete());
        logger.info("Plugin Enabled");
    }
    @Override
    public void onDisable() {
        // Send disable message
        Logger logger = plugin.getLogger();
        logger.info("Plugin Disabled");

    }
    public static SimpleJetpacks getPlugin(){
        return plugin;
    }
    public static void updateFuel(ItemStack item, int fuel){
        // Update the bar and the persistentdatacontainer
        ItemMeta chestplateMeta = item.getItemMeta();
        PersistentDataContainer chestplateData = chestplateMeta.getPersistentDataContainer();
        int maxFuel = chestplateData.get(new NamespacedKey(SimpleJetpacks.getPlugin(),"maxFuel"),PersistentDataType.INTEGER);
        Short durability = item.getType().getMaxDurability();
        chestplateData.set(new NamespacedKey(SimpleJetpacks.getPlugin(),"fuel"),PersistentDataType.INTEGER,fuel);
        ((Damageable) chestplateMeta).setDamage(Math.round(durability - (((float) fuel / maxFuel) * durability))); // update durability bar
        item.setItemMeta(chestplateMeta); // update worn jetpack
    }
    public static boolean isJetpack(ItemMeta meta){
        // Check if the meta has the jetpack data
        PersistentDataContainer data = meta.getPersistentDataContainer();
        if (data.get(new NamespacedKey(SimpleJetpacks.getPlugin(),"jetpack"), PersistentDataType.INTEGER)!= null){
            return  true;
        }
        else {return false;}
    }

}
