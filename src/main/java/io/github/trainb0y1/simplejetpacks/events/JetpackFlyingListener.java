package io.github.trainb0y1.simplejetpacks.events;

import io.github.trainb0y1.simplejetpacks.SimpleJetpacks;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
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
                int fuel = chestplateData.get(new NamespacedKey(SimpleJetpacks.getPlugin(), "fuel"), PersistentDataType.INTEGER);
                if (fuel <= 0) {
                    player.sendMessage(ChatColor.RED + "[SimpleJetpacks] Jetpack out of fuel!");
                    SimpleJetpacks.setJetpacking(player, false);
                    // Stop jetpacking to prevent empty jetpack from being free elytra and prevent spam
                    player.setGliding(false);
                    player.setSneaking(false);
                } else {
                    //
                    if (SimpleJetpacks.oldMotion) {
                        player.setVelocity(player.getLocation().getDirection().multiply(1.2).setY(0.5));
                    } else {
                        player.setVelocity(player.getLocation().getDirection().multiply(1.2));
                    }

                    for (String particle: SimpleJetpacks.getParticles(chestplateMeta)) {
                        player.getWorld().spawnParticle(Particle.valueOf(particle), player.getLocation(), 0);
                    }

                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CAT_PURR, 10, 1);
                    fuel -= SimpleJetpacks.getBurnRate(chestplateMeta); // but it won't because we already know its a jetpack
                    chestplateData.set(new NamespacedKey(SimpleJetpacks.getPlugin(), "fuel"), PersistentDataType.INTEGER, fuel);
                    int maxFuel = chestplateData.get(new NamespacedKey(SimpleJetpacks.getPlugin(), "maxFuel"), PersistentDataType.INTEGER);
                    // see comment above

                    if (fuel == maxFuel / 10) {
                        player.sendMessage(ChatColor.GOLD + "[SimpleJetpacks] Jetpack Fuel Low!");
                    }


                    SimpleJetpacks.updateFuel(player.getInventory().getChestplate(), fuel);
                }
            }
        }
    }
}
