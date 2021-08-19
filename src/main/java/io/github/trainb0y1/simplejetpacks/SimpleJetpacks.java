package io.github.trainb0y1.simplejetpacks;

import io.github.trainb0y1.simplejetpacks.commands.JetpackCommands;
import io.github.trainb0y1.simplejetpacks.commands.JetpackTabComplete;
import io.github.trainb0y1.simplejetpacks.events.*;
import io.github.trainb0y1.simplejetpacks.items.ItemManager;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.PluginManager;
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
        PluginManager manager = getServer().getPluginManager();

        manager.registerEvents(new RefuelEventListener(), this);
        manager.registerEvents(new JetpackFlyKickListener(), this);
        manager.registerEvents(new JetpackBreakListener(), this);
        manager.registerEvents(new JetpackToggleListener(), this);
        manager.registerEvents(new JetpackFlyingListener(), this);
        manager.registerEvents(new JetpackGlideListener(), this);
        manager.registerEvents(new JetpackEnchantListener(), this);

        oldMotion = this.getConfig().getBoolean("old-motion");

        if (this.getConfig().getBoolean("fuel-gui")){
            manager.registerEvents(new RefuelGUI(), this);
        }
        else {
            manager.registerEvents(new RefuelEventListener(), this);
        }

        getCommand("simplejetpacks").setExecutor(new JetpackCommands());
        getCommand("simplejetpacks").setTabCompleter(new JetpackTabComplete());
        logger.info("Plugin Enabled");
    }




    // Various utility functions

    public static SimpleJetpacks getPlugin() {
        return plugin;
    }

    public static void setFuel(ItemStack item, int fuel) {
        // Update the bar and the persistentdatacontainer
        ItemMeta chestplateMeta = item.getItemMeta();
        PersistentDataContainer chestplateData = chestplateMeta.getPersistentDataContainer();
        int maxFuel = chestplateData.get(new NamespacedKey(SimpleJetpacks.getPlugin(), "maxFuel"), PersistentDataType.INTEGER);
        Short durability = item.getType().getMaxDurability();
        chestplateData.set(new NamespacedKey(SimpleJetpacks.getPlugin(), "fuel"), PersistentDataType.INTEGER, fuel);
        ((Damageable) chestplateMeta).setDamage(Math.round(durability - (((float) fuel / maxFuel) * durability))); // update durability bar
        item.setItemMeta(chestplateMeta); // update worn jetpack
    }

    @Nullable
    public static Integer getFuel(ItemStack jetpack){
        ItemMeta chestplateMeta = jetpack.getItemMeta();
        PersistentDataContainer chestplateData = chestplateMeta.getPersistentDataContainer();
        return chestplateData.get(new NamespacedKey(SimpleJetpacks.getPlugin(), "fuel"), PersistentDataType.INTEGER);
    }

    @Nullable
    public static Integer getMaxFuel(ItemStack jetpack) {
        ItemMeta chestplateMeta = jetpack.getItemMeta();
        PersistentDataContainer chestplateData = chestplateMeta.getPersistentDataContainer();
        return chestplateData.get(new NamespacedKey(SimpleJetpacks.getPlugin(), "maxFuel"), PersistentDataType.INTEGER);
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
        if (data.get(new NamespacedKey(plugin, "jetpacking"), PersistentDataType.INTEGER) != null){
            return data.get(new NamespacedKey(plugin, "jetpacking"), PersistentDataType.INTEGER) == 1;
        }
        // they don't have the data, we can assume they aren't jetpacking
        data.set(new NamespacedKey(plugin, "jetpacking"), PersistentDataType.INTEGER, 0);
        // ^ shouldn't really have to do this, as if it should be true it's beem created, but whatever
        return false;


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
    public static Integer getBurnRate(ItemStack jetpack) {
        ItemMeta chestplateMeta = jetpack.getItemMeta();
        PersistentDataContainer data = chestplateMeta.getPersistentDataContainer();
        return data.get(new NamespacedKey(plugin, "burnRate"), PersistentDataType.INTEGER);
    }

    @Nullable
    public static Float getJetpackSpeed(ItemMeta meta) {
        PersistentDataContainer data = meta.getPersistentDataContainer();
        return data.get(new NamespacedKey(plugin, "speed"), PersistentDataType.FLOAT);
    }

    @Nullable
    public static String getJetpackSound(ItemMeta meta) {
        PersistentDataContainer data = meta.getPersistentDataContainer();
        return data.get(new NamespacedKey(plugin, "sound"), PersistentDataType.STRING);
    }

    @Nullable
    public static List<String> getParticles(ItemMeta meta) {
        // Returns a string list of particle names, can be used with
        // Particle.valueOf()
        if (!isJetpack(meta)) {
            return null;
        }
        PersistentDataContainer data = meta.getPersistentDataContainer();
        String particleString = data.get(new NamespacedKey(plugin, "particles"), PersistentDataType.STRING);
        return Arrays.asList(particleString.split(","));
    }
}
