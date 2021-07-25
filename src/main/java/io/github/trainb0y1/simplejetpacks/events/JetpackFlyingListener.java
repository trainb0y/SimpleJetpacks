package io.github.trainb0y1.simplejetpacks.events;

import io.github.trainb0y1.simplejetpacks.SimpleJetpacks;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


public class JetpackFlyingListener implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        // jetpack jetpacking
        Player player = event.getPlayer();
        if (SimpleJetpacks.isWearingJetpack(player) && player.isSneaking()) {
            ItemMeta chestplateMeta = player.getInventory().getChestplate().getItemMeta();
            PersistentDataContainer chestplateData = chestplateMeta.getPersistentDataContainer();

            if (SimpleJetpacks.isJetpacking(player)) {
                // In order to prevent glitching into the ground with the 0.5 glide movement, we have to disable jetpacking into the ground
                if (player.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR){
                    SimpleJetpacks.setJetpacking(player, false);
                    player.setGliding(false);
                    player.setSneaking(false); // *hopefully* this will prevent players gliding and glitching into blocks after they finish shifting
                    return; // Stop it
                }

                int fuel = chestplateData.get(new NamespacedKey(SimpleJetpacks.getPlugin(), "fuel"), PersistentDataType.INTEGER);
                if (fuel <= 0) {
                    player.sendMessage(ChatColor.RED + "[SimpleJetpacks] Jetpack out of fuel!");
                    SimpleJetpacks.setJetpacking(player,false);
                    // Stop jetpacking to prevent empty jetpack from being free elytra and prevent spam
                    player.setGliding(false);
                    player.setSneaking(false);
                } else {
                    //
                    if (SimpleJetpacks.oldMotion){
                        player.setVelocity(player.getLocation().getDirection().multiply(1.2).setY(0.5));
                    }
                    else {
                        player.setVelocity(player.getLocation().getDirection().multiply(1.2));
                    }


                    player.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, player.getLocation(), 0);
                    player.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, player.getLocation(), 0);
                    player.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, player.getLocation(), 0);
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CAT_PURR, 10, 1);
                    fuel -= 1;
                    chestplateData.set(new NamespacedKey(SimpleJetpacks.getPlugin(), "fuel"), PersistentDataType.INTEGER, fuel);
                    int maxFuel = chestplateData.get(new NamespacedKey(SimpleJetpacks.getPlugin(),"maxFuel"),PersistentDataType.INTEGER);

                    if (fuel == maxFuel / 10) {
                        player.sendMessage(ChatColor.GOLD + "[SimpleJetpacks] Jetpack Fuel Low!");
                    }


                    SimpleJetpacks.updateFuel(player.getInventory().getChestplate(),fuel);
                }
            }
        }
    }
}
