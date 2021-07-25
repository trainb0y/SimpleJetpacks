package io.github.trainb0y1.simplejetpacks.events;

import io.github.trainb0y1.simplejetpacks.SimpleJetpacks;
import io.github.trainb0y1.simplejetpacks.items.ItemManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class JetpackBreakListener implements Listener {
    /*
    Prevent a jetpack from breaking when fuel (and durability bar)
    reach 0
     */

    @EventHandler
    public void onJetpackBreak(PlayerItemBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getBrokenItem();
        if (SimpleJetpacks.isJetpack(item.getItemMeta())) {
            Material mat = item.getType();
            // can assume its being worn if it broke, so replace it with a durability 1 one
            player.getInventory().setChestplate(ItemManager.getJetpack(mat)); // Replace it with the same type
            // Player has a new jetpack, set the durability to 1
            ItemMeta meta = player.getInventory().getChestplate().getItemMeta();
            ((Damageable) meta).setDamage(mat.getMaxDurability() - 1); // make it ALMOST broken
            player.getInventory().getChestplate().setItemMeta(meta);

        }

    }
}