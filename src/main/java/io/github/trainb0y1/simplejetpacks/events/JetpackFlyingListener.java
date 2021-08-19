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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;


public class JetpackFlyingListener implements Listener {
    /*
    Handle flying with a jetpack
     */

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!(SimpleJetpacks.isWearingJetpack(player) && player.isSneaking())) {
            return;
        }

        ItemStack chestplate = player.getInventory().getChestplate();
        ItemMeta chestplateMeta = chestplate.getItemMeta();

        if (!SimpleJetpacks.isJetpacking(player)) {
            return;
        }

        int fuel = SimpleJetpacks.getFuel(chestplate);

        if (fuel <= 0) {
            player.sendMessage(ChatColor.RED + "[SimpleJetpacks] Jetpack out of fuel!");
            SimpleJetpacks.setJetpacking(player, false);
            // Stop jetpacking to prevent empty jetpack from being free elytra and prevent spam
            player.setGliding(false);
            player.setSneaking(false);
            return;
        }

        Float speed = SimpleJetpacks.getJetpackSpeed(chestplateMeta);

        if (SimpleJetpacks.oldMotion) {
            player.setVelocity(player.getLocation().getDirection().multiply(speed).setY(0.5));
        } else {
            player.setVelocity(player.getLocation().getDirection().multiply(speed));
        }

        for (String particle : SimpleJetpacks.getParticles(chestplateMeta)) {
            player.getWorld().spawnParticle(Particle.valueOf(particle), player.getLocation(), 0);
        }

        player.getWorld().playSound(
                player.getLocation(),
                Sound.valueOf(SimpleJetpacks.getJetpackSound(chestplateMeta)),
                10,
                1
        );


        fuel -= SimpleJetpacks.getBurnRate(chestplate); // but it won't because we already know its a jetpack
        SimpleJetpacks.setFuel(chestplate, fuel);


        int maxFuel = SimpleJetpacks.getMaxFuel(chestplate);

        if (fuel == maxFuel / 10) {
            player.sendMessage(ChatColor.GOLD + "[SimpleJetpacks] Jetpack Fuel Low!");
        }
    }
}
