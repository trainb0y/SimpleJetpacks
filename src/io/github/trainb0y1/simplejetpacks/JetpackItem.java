package io.github.trainb0y1.simplejetpacks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class JetpackItem {
    public static ItemStack jetpack;

    public static void init() {
        createJetpack();
    }

    private static void createJetpack() {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE,1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Jetpack");
        List<String> lore = new ArrayList<>();
        lore.add("§7Consumes coal as fuel");
        meta.setLore(lore);
        item.setItemMeta(meta);


        jetpack = item;
    }
}
