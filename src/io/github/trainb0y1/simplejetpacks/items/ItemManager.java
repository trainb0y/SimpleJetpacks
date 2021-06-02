package io.github.trainb0y1.simplejetpacks.items;

import io.github.trainb0y1.simplejetpacks.SimpleJetpacks;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    public static ItemStack jetpack;

    public static void createJetpack(JavaPlugin plugin) {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE,1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Jetpack");
        List<String> lore = new ArrayList<>();
        lore.add("§7Right click with fuel item");
        lore.add("§7while wearing to add fuel.");
        meta.setLore(lore);
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(new NamespacedKey(SimpleJetpacks.getPlugin(),"jetpack"), PersistentDataType.INTEGER, 1);
        // Value is arbitrary, it just has to have this data.

        item.setItemMeta(meta);

        NamespacedKey key = new NamespacedKey(plugin, "jetpack");
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("IPI","ICI","IRI");
        recipe.setIngredient('I',Material.IRON_INGOT);
        recipe.setIngredient('P',Material.PISTON);
        recipe.setIngredient('C',Material.LEATHER_CHESTPLATE);
        recipe.setIngredient('R',Material.REDSTONE_BLOCK);

        Bukkit.addRecipe(recipe);

        jetpack = item;
    }
}
