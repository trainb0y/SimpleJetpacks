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
            ItemMeta meta = item.getItemMeta(); // get a copy of the current meta
            ((Damageable) meta).setDamage(mat.getMaxDurability() - 1); // but make it not broken
            player.getInventory().setChestplate(ItemManager.getJetpack(mat)); // give the player a new jetpack
            player.getInventory().getChestplate().setItemMeta(meta); // and apply the old jetpack's meta
        }
    }
}