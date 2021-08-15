package io.github.trainb0y1.simplejetpacks;

import io.github.trainb0y1.simplejetpacks.commands.JetpackCommands;
import io.github.trainb0y1.simplejetpacks.commands.JetpackTabComplete;
import io.github.trainb0y1.simplejetpacks.events.*;
import io.github.trainb0y1.simplejetpacks.items.ItemManager;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;


public class SimpleJetpacks extends JavaPlugin {
    private static SimpleJetpacks plugin;
    public static boolean oldMotion;

    @Override
    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();
        Logger logger = this.getLogger();

        // Register the stuff we need to register
        ItemManager.createJetpacks(this);

        getServer().getPluginManager().registerEvents(new RefuelEventListener(), this);
        getServer().getPluginManager().registerEvents(new JetpackFlyKickListener(), this);
        getServer().getPluginManager().registerEvents(new JetpackBreakListener(), this);
        getServer().getPluginManager().registerEvents(new JetpackToggleListener(), this);
        getServer().getPluginManager().registerEvents(new JetpackFlyingListener(), this);
        getServer().getPluginManager().registerEvents(new JetpackGlideListener(), this);
        getServer().getPluginManager().registerEvents(new JetpackEnchantListener(), this);

        oldMotion = this.getConfig().getBoolean("old-motion");

        getCommand("simplejetpacks").setExecutor(new JetpackCommands());
        getCommand("simplejetpacks").setTabCompleter(new JetpackTabComplete());
        logger.info("Plugin Enabled");
    }


    public static SimpleJetpacks getPlugin() {
        return plugin;
    }

    public static void updateFuel(ItemStack item, int fuel) {
        // Update the bar and the persistentdatacontainer
        ItemMeta chestplateMeta = item.getItemMeta();
        PersistentDataContainer chestplateData = chestplateMeta.getPersistentDataContainer();
        int maxFuel = chestplateData.get(new NamespacedKey(SimpleJetpacks.getPlugin(), "maxFuel"), PersistentDataType.INTEGER);
        Short durability = item.getType().getMaxDurability();
        chestplateData.set(new NamespacedKey(SimpleJetpacks.getPlugin(), "fuel"), PersistentDataType.INTEGER, fuel);
        ((Damageable) chestplateMeta).setDamage(Math.round(durability - (((float) fuel / maxFuel) * durability))); // update durability bar
        item.setItemMeta(chestplateMeta); // update worn jetpack
    }

    public static boolean isJetpack(ItemMeta meta) {
        // Check if the meta has the jetpack data
        PersistentDataContainer data = meta.getPersistentDataContainer();
        return data.get(new NamespacedKey(SimpleJetpacks.getPlugin(), "jetpack"), PersistentDataType.INTEGER) != null;
    }

    public static boolean isWearingJetpack(Player player) {
        if (player.getInventory().getChestplate() == null) {
            return false;
        }
        if (player.getInventory().getChestplate().getItemMeta() == null) {
            return false;
        }
        return isJetpack(player.getInventory().getChestplate().getItemMeta());
    }

    public static boolean isJetpacking(Player player) {
        if (!isWearingJetpack(player)) {
            return false;
        }
        PersistentDataContainer data = player.getPersistentDataContainer();
        int jetpacking = data.get(new NamespacedKey(plugin, "jetpacking"), PersistentDataType.INTEGER);
        return jetpacking == 1;

    }

    public static void setJetpacking(Player player, boolean jetpacking) {
        int num = 0;
        if (jetpacking) {
            num = 1;
        }
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(new NamespacedKey(plugin, "jetpacking"), PersistentDataType.INTEGER, num);
    }

    @Nullable
    public static Integer getBurnRate(ItemMeta meta){
        PersistentDataContainer data = meta.getPersistentDataContainer();
        return data.get(new NamespacedKey(plugin, "burnRate"), PersistentDataType.INTEGER);
    }
    @Nullable
    public static List<String> getParticles(ItemMeta meta){
        // Returns a string list of particle names, can be used with
        // Particle.valueOf()
        if (!isJetpack(meta)){
            return null;
        }
        PersistentDataContainer data = meta.getPersistentDataContainer();
        String particleString = data.get(new NamespacedKey(plugin,"particles"), PersistentDataType.STRING);
        return Arrays.asList(particleString.split(","));
    }
}
