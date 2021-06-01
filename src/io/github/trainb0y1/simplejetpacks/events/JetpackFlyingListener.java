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
            if (player.isSneaking() && SimpleJetpacks.isJetpack(player.getInventory().getChestplate().getItemMeta())){
                PersistentDataContainer data = player.getPersistentDataContainer();
                if (data.get(new NamespacedKey(SimpleJetpacks.getPlugin(), "jetpacking"), PersistentDataType.INTEGER) == 1) {

                    if (data.get(new NamespacedKey(SimpleJetpacks.getPlugin(), "jetpackFuel"), PersistentDataType.INTEGER) == null){
                        data.set(new NamespacedKey(SimpleJetpacks.getPlugin(), "jetpackFuel"), PersistentDataType.INTEGER,1000);
                    }
                    int fuel = data.get(new NamespacedKey(SimpleJetpacks.getPlugin(), "jetpackFuel"), PersistentDataType.INTEGER);
                    if (fuel <= 0) {
                        player.sendMessage(ChatColor.RED + "[SimpleJetpacks] Jetpack out of fuel!");
                    } else {

                        player.setVelocity(player.getLocation().getDirection().multiply(1.2).setY(0.5));
                        player.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, player.getLocation(), 0);
                        player.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, player.getLocation(), 0);
                        player.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, player.getLocation(), 0);
                        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CAT_PURR, 10, 1);

                        ItemMeta meta = player.getInventory().getChestplate().getItemMeta();

                        //player.sendMessage("Old Fuel: "+Integer.toString(fuel));
                        fuel -= 1;
                        //player.sendMessage("New Fuel: "+Integer.toString(fuel));
                        data.set(new NamespacedKey(SimpleJetpacks.getPlugin(), "jetpackFuel"), PersistentDataType.INTEGER, fuel);
                        //player.sendMessage("Fuel/Max Fuel: "+Double.toString((float)fuel/maxFuel));
                        //player.sendMessage("New Durability: "+Integer.toString(Math.round(((float)fuel / maxFuel)*80)));
                        //player.sendMessage("New Damage: "+Integer.toString(Math.round(80-(((float)fuel / maxFuel)*80))));
                        int maxFuel = SimpleJetpacks.getPlugin().getConfig().getInt("max-fuel");
                        
                        if (fuel == maxFuel / 10) {
                            player.sendMessage(ChatColor.GOLD + "[SimpleJetpacks] Jetpack Fuel Low!");
                        }
                        
                        ((Damageable) meta).setDamage(Math.round(80 - (((float) fuel / maxFuel) * 80)));
                        player.getInventory().getChestplate().setItemMeta(meta);

                    }
                }
            }
        }
    }
}
