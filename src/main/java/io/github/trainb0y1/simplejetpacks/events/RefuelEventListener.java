package io.github.trainb0y1.simplejetpacks.events;

import io.github.trainb0y1.simplejetpacks.SimpleJetpacks;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;


public class RefuelEventListener implements Listener {
    /*
    Jetpack refueling:
        jetpack to jetpack fueling
        item to jetpack fueling
     This only triggers if fuel-gui is false in the config
     Kind of a mess, *shrug*
     */

    @EventHandler
    public void onRefuel(PlayerInteractEvent event) {
        // If the player right click with coal, fuel up by 100
        Player player = event.getPlayer();
        if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (!(SimpleJetpacks.isWearingJetpack(player) && event.getItem() != null)) {
            return;
        }
        ItemStack chestplate = player.getInventory().getChestplate();

        int maxFuel = SimpleJetpacks.getMaxFuel(chestplate);
        int fuel = SimpleJetpacks.getFuel(chestplate);

        // First check if they are holding a jetpack for fuel transfer
        if (SimpleJetpacks.isJetpack(event.getItem().getItemMeta())) {
            if (fuel == maxFuel) { // I would put this up above when we first get maxFuel but then it will do it on a right click with ANY item
                player.sendMessage(ChatColor.GREEN + "[SimpleJetpacks] Fuel already full!");
                return;
            }
            ItemStack heldJetpack = event.getItem();
            ItemMeta heldJetpackMeta = heldJetpack.getItemMeta();
            PersistentDataContainer heldJetpackData = heldJetpackMeta.getPersistentDataContainer();
            int fuelHeld = SimpleJetpacks.getFuel(event.getItem());
            int heldMaxFuel = SimpleJetpacks.getMaxFuel(heldJetpack);
            int neededFuel = maxFuel - fuel;
            if (neededFuel > fuelHeld) {
                fuel += fuelHeld;
                player.sendMessage(ChatColor.GREEN + "[SimpleJetpacks] +" + fuelHeld + " fuel!");
                fuelHeld = 0;
            } else {
                fuel = maxFuel;
                fuelHeld -= neededFuel;
                player.sendMessage(ChatColor.GREEN + "[SimpleJetpacks] Jetpack refilled, fuel remaining: " + fuelHeld);
            }
            SimpleJetpacks.setFuel(heldJetpack, fuelHeld);
        }

        // They are wearing a jetpack, but not holding one. Find out if they are holding a fuel item
        // Can't use foreach because "java: local variables referenced from a lambda expression must be final or effectively final"
        for (String key : SimpleJetpacks.getPlugin().getConfig().getConfigurationSection("items").getKeys(false)) {

            if (!key.equalsIgnoreCase(event.getItem().getType().toString())) {
                continue; // the clicked item is not a fuel item
            }

            if (fuel == maxFuel) { // I would put this up above when we first get maxFuel but then it will do it on a right click with ANY item
                player.sendMessage(ChatColor.GREEN + "[SimpleJetpacks] Fuel already full!");
                return;
            }

            // They are holding this item
            int itemFuelValue = SimpleJetpacks.getPlugin().getConfig().getInt("items." + key);

            fuel += itemFuelValue;
            if (fuel >= maxFuel) {
                fuel = maxFuel;
            }
            player.sendMessage(ChatColor.GREEN + "[SimpleJetpacks] +" + itemFuelValue + " Fuel! (" + +fuel + "/" + maxFuel + ")");
            // Remove an item from the hand
            ItemStack hand = event.getItem();
            hand.setAmount(hand.getAmount() - 1);
            player.getInventory().setItemInMainHand(hand);
        }

        SimpleJetpacks.setFuel(chestplate, fuel);
    }
}
