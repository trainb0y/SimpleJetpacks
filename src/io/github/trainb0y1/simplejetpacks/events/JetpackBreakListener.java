package io.github.trainb0y1.simplejetpacks.events;

import io.github.trainb0y1.simplejetpacks.SimpleJetpacks;
import io.github.trainb0y1.simplejetpacks.items.ItemManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class JetpackBreakListener implements Listener {

    @EventHandler
    public void onJetpackBreak(PlayerItemBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getBrokenItem();
        if (SimpleJetpacks.isJetpack(item.getItemMeta())) {
            // can assume its being worn if it broke, so replace it with a durability 1 one
            player.getInventory().setChestplate(ItemManager.jetpack);
            // Player has a new jetpack, set the durability to 1
            ItemMeta meta = player.getInventory().getChestplate().getItemMeta();
            ((Damageable) meta).setDamage(79);
            player.getInventory().getChestplate().setItemMeta(meta);

        }

    }
}