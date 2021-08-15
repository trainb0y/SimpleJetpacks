package io.github.trainb0y1.simplejetpacks.items;

import io.github.trainb0y1.simplejetpacks.SimpleJetpacks;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    /*
    Create and manage the jetpack itemstacks
     */
    public static ArrayList<ItemStack> jetpacks;
    public static void createJetpacks(JavaPlugin plugin) {
        jetpacks = new ArrayList<ItemStack>();
        // Iterate through all jetpacks in the config and add them to the list
        FileConfiguration config = plugin.getConfig();

        for (String itemKey : config.getConfigurationSection("jetpacks").getKeys(false)) {
            int maxFuel = config.getInt("jetpacks." + itemKey + ".fuel-capacity");
            int burnRate = config.getInt("jetpacks." + itemKey + ".burn-rate");
            float speed = (float) config.getDouble("jetpacks." + itemKey + ".fly-speed");
            String sound = config.getString("jetpacks." + itemKey + ".sound");
            List<String> particles = config.getStringList("jetpacks." + itemKey + ".particles");


            ItemStack item = new ItemStack(Material.getMaterial(itemKey), 1);

            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName("§6Jetpack");
            List<String> lore = new ArrayList<>();
            lore.add("§7Right click with fuel item");
            lore.add("§7while wearing to add fuel");
            lore.add("§7Fuel Capacity: " + maxFuel);
            meta.setLore(lore);


            PersistentDataContainer data = meta.getPersistentDataContainer();
            // Value of "jetpack" is arbitrary, it just has to have this data.
            data.set(new NamespacedKey(plugin, "jetpack"), PersistentDataType.INTEGER, 1);
            data.set(new NamespacedKey(plugin, "fuel"), PersistentDataType.INTEGER, 0);
            data.set(new NamespacedKey(plugin, "maxFuel"), PersistentDataType.INTEGER, maxFuel);
            data.set(new NamespacedKey(plugin, "burnRate"), PersistentDataType.INTEGER, burnRate);
            data.set(new NamespacedKey(plugin, "speed"), PersistentDataType.FLOAT, speed);
            data.set(new NamespacedKey(plugin, "sound"), PersistentDataType.STRING, sound);
            data.set(new NamespacedKey(plugin,"particles"), PersistentDataType.STRING, String.join(",",particles));
            // Particles are stored as a csv string of particle names
            item.setItemMeta(meta);

            NamespacedKey key = new NamespacedKey(plugin, "jetpack" + itemKey); // Separate recipe keys for each pack
            ShapedRecipe recipe = new ShapedRecipe(key, item);
            recipe.shape("IPI", "ICI", "IRI");
            recipe.setIngredient('I', Material.IRON_INGOT);
            recipe.setIngredient('P', Material.PISTON);
            recipe.setIngredient('C', Material.getMaterial(itemKey));
            recipe.setIngredient('R', Material.REDSTONE_BLOCK);

            Bukkit.addRecipe(recipe);

            jetpacks.add(item);
        }
        if (jetpacks == null) {
            plugin.getLogger().warning("No jetpacks defined! Define one in config.yml");
        }
    }

    @Nullable
    public static ItemStack getJetpack(Material baseItem) {
        for (ItemStack item : jetpacks) {
            if (item.getType().equals(baseItem)) {
                return item;
            }
        }
        return null;
    }
}
