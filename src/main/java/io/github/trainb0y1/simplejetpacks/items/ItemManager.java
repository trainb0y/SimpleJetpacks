package io.github.trainb0y1.simplejetpacks.items;

import io.github.trainb0y1.simplejetpacks.SimpleJetpacks;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    /*
    Create and manage the jetpack itemstacks
     */
    public static ArrayList<ItemStack> jetpacks;

    public static void createJetpacks(SimpleJetpacks plugin) {
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

            meta.displayName(Component.text("Jetpack", NamedTextColor.GOLD));
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("Fuel Capacity: " + maxFuel, NamedTextColor.DARK_GREEN));
            lore.add(Component.text("Fuel Burn Rate: " + burnRate, NamedTextColor.DARK_GREEN));
            lore.add(Component.text("Speed: " + speed, NamedTextColor.DARK_GREEN));
            meta.lore(lore);


            PersistentDataContainer data = meta.getPersistentDataContainer();
            // Value of "jetpack" is arbitrary, it just has to have this data.
            data.set(new NamespacedKey(plugin, "jetpack"), PersistentDataType.INTEGER, 1);
            data.set(new NamespacedKey(plugin, "fuel"), PersistentDataType.INTEGER, 0);
            data.set(new NamespacedKey(plugin, "maxFuel"), PersistentDataType.INTEGER, maxFuel);
            data.set(new NamespacedKey(plugin, "burnRate"), PersistentDataType.INTEGER, burnRate);
            data.set(new NamespacedKey(plugin, "speed"), PersistentDataType.FLOAT, speed);
            data.set(new NamespacedKey(plugin, "sound"), PersistentDataType.STRING, sound);
            data.set(new NamespacedKey(plugin, "particles"), PersistentDataType.STRING, String.join(",", particles));
            // Particles are stored as a csv string of particle names
            item.setItemMeta(meta);

            // now for the hardest part, getting the recipe from the config
            NamespacedKey key = new NamespacedKey(plugin, "jetpack" + itemKey); // Separate recipe keys for each pack
            ShapedRecipe recipe = new ShapedRecipe(key, item);
            recipe.shape(
                    config.getString("jetpacks." + itemKey + ".recipe.r1"),
                    config.getString("jetpacks." + itemKey + ".recipe.r2"),
                    config.getString("jetpacks." + itemKey + ".recipe.r3")
            );

            for (String craftItemKey :
                    config.getConfigurationSection(
                            "jetpacks." + itemKey + ".recipe.items"
                    ).getKeys(false)) {
                // For each key, add key, item to the recipe
                recipe.setIngredient(craftItemKey.charAt(0),
                        Material.getMaterial(config.getString("jetpacks." + itemKey + ".recipe.items." + craftItemKey)));
            }

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
