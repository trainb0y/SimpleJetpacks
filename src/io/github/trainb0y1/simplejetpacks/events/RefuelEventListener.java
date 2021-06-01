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
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


public class RefuelEventListener implements Listener {
    @EventHandler
    public void onRefuel(PlayerInteractEvent event){
        // If the player right click with coal, fuel up by 100
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getPlayer().getInventory().getChestplate() != null && event.getItem() != null) {
                if (SimpleJetpacks.isJetpack(player.getInventory().getChestplate().getItemMeta())) {
                    // They are wearing a jetpack. Find out if they are holding a fuel item
                    SimpleJetpacks.getPlugin().getConfig().getConfigurationSection("items").getKeys(false).forEach(key -> {
                        if (key.equalsIgnoreCase(event.getItem().getType().toString())){


                            // They are holding this item
                            int itemFuel =  SimpleJetpacks.getPlugin().getConfig().getInt("items." + key);

                            PersistentDataContainer data = player.getPersistentDataContainer();

                            int fuel = data.get(new NamespacedKey(SimpleJetpacks.getPlugin(),"jetpackFuel"), PersistentDataType.INTEGER);;
                            int maxFuel = SimpleJetpacks.getPlugin().getConfig().getInt("max-fuel");

                            if (fuel == maxFuel){
                                player.sendMessage(ChatColor.GREEN+"[SimpleJetpacks] Fuel already full!");
                            }
                            else {
                                fuel += itemFuel;
                                if (fuel >= maxFuel) {
                                    fuel = maxFuel;
                                }
                                player.sendMessage(ChatColor.GREEN + "[SimpleJetpacks] +"+itemFuel+" Fuel! ("+ + fuel + "/" + maxFuel);
                                data.set(new NamespacedKey(SimpleJetpacks.getPlugin(),"jetpackFuel"),PersistentDataType.INTEGER,fuel);
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
