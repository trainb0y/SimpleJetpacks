package io.github.trainb0y1.simplejetpacks.events;

import io.github.trainb0y1.simplejetpacks.SimpleJetpacks;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


public class JetpackFlyingListener implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        // jetpack jetpacking
        Player player = event.getPlayer();
        if (event.getPlayer().getInventory().getChestplate() != null) {
            ItemMeta chestplateMeta = player.getInventory().getChestplate().getItemMeta();
            if (player.isSneaking() && SimpleJetpacks.isJetpack(chestplateMeta)){
                PersistentDataContainer chestplateData = chestplateMeta.getPersistentDataContainer();
                PersistentDataContainer playerData = player.getPersistentDataContainer();

                if (playerData.get(new NamespacedKey(SimpleJetpacks.getPlugin(), "jetpacking"), PersistentDataType.INTEGER) == 1) {

                    int fuel = chestplateData.get(new NamespacedKey(SimpleJetpacks.getPlugin(), "fuel"), PersistentDataType.INTEGER);
                    if (fuel <= 0) {
                        player.sendMessage(ChatColor.RED + "[SimpleJetpacks] Jetpack out of fuel!");
                    } else {

                        player.setVelocity(player.getLocation().getDirection().multiply(1.2).setY(0.5));
                        player.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, player.getLocation(), 0);
                        player.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, player.getLocation(), 0);
                        player.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, player.getLocation(), 0);
                        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CAT_PURR, 10, 1);

                        //player.sendMessage("Old Fuel: "+Integer.toString(fuel));
                        fuel -= 1;
                        //player.sendMessage("New Fuel: "+Integer.toString(fuel));
                        chestplateData.set(new NamespacedKey(SimpleJetpacks.getPlugin(), "fuel"), PersistentDataType.INTEGER, fuel);
                        //player.sendMessage("Fuel/Max Fuel: "+Double.toString((float)fuel/maxFuel));
                        //player.sendMessage("New Durability: "+Integer.toString(Math.round(((float)fuel / maxFuel)*80)));
                        //player.sendMessage("New Damage: "+Integer.toString(Math.round(80-(((float)fuel / maxFuel)*80))));
                        int maxFuel = chestplateData.get(new NamespacedKey(SimpleJetpacks.getPlugin(),"maxFuel"),PersistentDataType.INTEGER);

                        if (fuel == maxFuel / 10) {
                            player.sendMessage(ChatColor.GOLD + "[SimpleJetpacks] Jetpack Fuel Low!");
                        }


                        Short durability = event.getPlayer().getInventory().getChestplate().getType().getMaxDurability();
                        chestplateData.set(new NamespacedKey(SimpleJetpacks.getPlugin(),"fuel"),PersistentDataType.INTEGER,fuel);
                        ((Damageable) chestplateMeta).setDamage(Math.round(durability - (((float) fuel / maxFuel) * durability))); // update durability bar
                        player.getInventory().getChestplate().setItemMeta(chestplateMeta); // update worn jetpack

                    }
                }
            }
        }
    }
}
