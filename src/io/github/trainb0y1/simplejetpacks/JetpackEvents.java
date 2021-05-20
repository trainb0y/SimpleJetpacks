package io.github.trainb0y1.simplejetpacks;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class JetpackEvents implements Listener {
    public ArrayList<UUID> jetpackers = new ArrayList<>();
    public HashMap<UUID, Integer> jetpackFuel = new HashMap<UUID, Integer>();
    public int maxFuel = 1000;
    private JavaPlugin plugin;

    public JetpackEvents(JavaPlugin plugin){
        this.plugin = plugin;
    }

    public boolean isSameCustomItem(ItemMeta meta1, ItemMeta meta2){
        // checks if item metas are the same, by checking name and lore
        if (!meta1.getDisplayName().equals(meta2.getDisplayName())) {
            return false;
        }
        return meta1.getLore().equals(meta2.getLore());
    }

    @EventHandler
    public void onShiftPressed(PlayerToggleSneakEvent event) {
        // jetpack toggle
        Player player = event.getPlayer();
        if (event.getPlayer().getInventory().getChestplate() != null) {
            if (isSameCustomItem(player.getInventory().getChestplate().getItemMeta(),JetpackItem.jetpack.getItemMeta())) {
                // if the player is in the air (they jumped) enable jetpack for them
                if ( !this.jetpackers.contains(player.getUniqueId()) &&
                        (player.getLocation().subtract(0,1,0).getBlock().getType() == Material.AIR)) { //&&
                    //(player.isSneaking())){
                    // player.issneaking prevents unshifting in midair to start, which leads you to only fall WHILE SHIFTING
                    this.jetpackers.add(player.getUniqueId());
                    // Check if they have a jetpack fuel yet, if not, make one
                    if (!jetpackFuel.containsKey(player.getUniqueId())){
                        jetpackFuel.put(player.getUniqueId(),maxFuel);
                    }
                }
                else if (this.jetpackers.contains(player.getUniqueId())){
                    this.jetpackers.remove(player.getUniqueId());
                }
            }
        }
    }
    @EventHandler
    public void onMove(PlayerMoveEvent event){
        // jetpack jetpacking
        Player player = event.getPlayer();
        if (event.getPlayer().getInventory().getChestplate() != null) {
            if (player.isSneaking() && isSameCustomItem(player.getInventory().getChestplate().getItemMeta(),JetpackItem.jetpack.getItemMeta()) && this.jetpackers.contains(player.getUniqueId())) {
                player.setVelocity(player.getLocation().getDirection().multiply(1.2).setY(0.5));
                player.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, player.getLocation(), 0);
                player.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, player.getLocation(), 0);
                player.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, player.getLocation(), 0);
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CAT_PURR,10,1);

                ItemMeta meta = player.getInventory().getChestplate().getItemMeta();

                int fuel = jetpackFuel.get(player.getUniqueId());
                //player.sendMessage("Old Fuel: "+Integer.toString(fuel));
                fuel -= 1;
                //player.sendMessage("New Fuel: "+Integer.toString(fuel));
                jetpackFuel.replace(player.getUniqueId(),fuel);
                //player.sendMessage("Fuel/Max Fuel: "+Double.toString((float)fuel/maxFuel));
                //player.sendMessage("New Durability: "+Integer.toString(Math.round(((float)fuel / maxFuel)*80)));
                //player.sendMessage("New Damage: "+Integer.toString(Math.round(80-(((float)fuel / maxFuel)*80))));
                ((Damageable) meta).setDamage(Math.round(80-(((float)fuel / maxFuel)*80)));
                player.getInventory().getChestplate().setItemMeta(meta);
            }
        }
    }
    @EventHandler
    public void onKicked(PlayerKickEvent event) {
        // Prevent fly kicking while jetpacking
        Player player = event.getPlayer();
        if (event.getPlayer().getInventory().getChestplate() != null) {
            if (isSameCustomItem(player.getInventory().getChestplate().getItemMeta(),JetpackItem.jetpack.getItemMeta())) {
                if (event.getReason().contains("Flying")) {
                    event.setCancelled(true);
                }
            }
        }
    }

}
