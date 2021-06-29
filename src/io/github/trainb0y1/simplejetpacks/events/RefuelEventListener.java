package io.github.trainb0y1.simplejetpacks.events;

import io.github.trainb0y1.simplejetpacks.SimpleJetpacks;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


public class RefuelEventListener implements Listener {
    @EventHandler
    public void onRefuel(PlayerInteractEvent event){
        // If the player right click with coal, fuel up by 100
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getPlayer().getInventory().getChestplate() != null && event.getItem() != null) {
                ItemMeta chestplateMeta = player.getInventory().getChestplate().getItemMeta();
                if (SimpleJetpacks.isJetpack(chestplateMeta)) {
                    // They are wearing a jetpack. Find out if they are holding a fuel item
                    SimpleJetpacks.getPlugin().getConfig().getConfigurationSection("items").getKeys(false).forEach(key -> {
                        if (key.equalsIgnoreCase(event.getItem().getType().toString())){


                            // They are holding this item
                            int itemFuel =  SimpleJetpacks.getPlugin().getConfig().getInt("items." + key);
                            PersistentDataContainer chestplateData = chestplateMeta.getPersistentDataContainer();
                            PersistentDataContainer playerData = player.getPersistentDataContainer();

                            int fuel = chestplateData.get(new NamespacedKey(SimpleJetpacks.getPlugin(),"fuel"), PersistentDataType.INTEGER);
                            int maxFuel = chestplateData.get(new NamespacedKey(SimpleJetpacks.getPlugin(),"maxFuel"), PersistentDataType.INTEGER);

                            if (fuel == maxFuel){
                                player.sendMessage(ChatColor.GREEN+"[SimpleJetpacks] Fuel already full!");
                            }
                            else {
                                fuel += itemFuel;
                                if (fuel >= maxFuel) {
                                    fuel = maxFuel;
                                }
                                player.sendMessage(ChatColor.GREEN + "[SimpleJetpacks] +"+itemFuel+" Fuel! ("+ + fuel + "/" + maxFuel + ")");
                                chestplateData.set(new NamespacedKey(SimpleJetpacks.getPlugin(),"fuel"),PersistentDataType.INTEGER,fuel);
                                player.getInventory().getChestplate().setItemMeta(chestplateMeta); // update the meta
                                // Remove an item from the hand
                                ItemStack hand = event.getItem();
                                hand.setAmount(hand.getAmount() - 1);
                                player.getInventory().setItemInMainHand(hand);
                            }
                        }
                    });
                }
            }
        }
    }
}
