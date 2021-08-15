package io.github.trainb0y1.simplejetpacks.items;

import io.github.trainb0y1.simplejetpacks.SimpleJetpacks;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ItemManager {
    /*
    Create and manage the jetpack itemstacks
     */
    public static ArrayList<ItemStack> jetpacks;
    public static void createJetpacks(JavaPlugin plugin) {
        jetpacks = new ArrayList<ItemStack>();
        // Iterate through all jetpacks in the config and add them to the list
        for (String itemKey : SimpleJetpacks.getPlugin().getConfig().getConfigurationSection("jetpacks").getKeys(false)) {
            int maxFuel = SimpleJetpacks.getPlugin().getConfig().getInt("jetpacks." + itemKey + ".fuel-capacity");
            int burnRate = SimpleJetpacks.getPlugin().getConfig().getInt("jetpacks." + itemKey + ".burn-rate");
            List<String> particles = SimpleJetpacks.getPlugin().getConfig().getStringList("jetpacks." + itemKey + ".particles");




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
            data.set(new NamespacedKey(SimpleJetpacks.getPlugin(), "jetpack"), PersistentDataType.INTEGER, 1);
            data.set(new NamespacedKey(SimpleJetpacks.getPlugin(), "fuel"), PersistentDataType.INTEGER, 0);
            data.set(new NamespacedKey(SimpleJetpacks.getPlugin(), "maxFuel"), PersistentDataType.INTEGER, maxFuel);
            data.set(new NamespacedKey(SimpleJetpacks.getPlugin(), "burnRate"), PersistentDataType.INTEGER, burnRate);
            data.set(new NamespacedKey(SimpleJetpacks.getPlugin(),"particles"), PersistentDataType.STRING, String.join(",",particles));
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
            SimpleJetpacks.getPlugin().getLogger().warning("No jetpacks defined! Define one in config.yml");
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
