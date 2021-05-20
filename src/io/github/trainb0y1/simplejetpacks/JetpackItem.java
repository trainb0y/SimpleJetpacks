package io.github.trainb0y1.simplejetpacks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class JetpackItem {
    public static ItemStack jetpack;

    public static void init(JavaPlugin plugin) {
        createJetpack(plugin);
    }

    private static void createJetpack(JavaPlugin plugin) {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE,1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Jetpack");
        List<String> lore = new ArrayList<>();
        lore.add("§7Consumes coal as fuel");
        meta.setLore(lore);
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
