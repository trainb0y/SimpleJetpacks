package io.github.trainb0y1.simplejetpacks.events;

import io.github.trainb0y1.simplejetpacks.SimpleJetpacks;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;


public class JetpackGlideListener implements Listener {
    /*
    Keep the player in glide mode while jetpacking
     */
    @EventHandler
    public void onGlideToggle(EntityToggleGlideEvent event) {
        if (SimpleJetpacks.oldMotion) {
            return;
        }
        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) {
            return;
        }

        Player player = (Player) entity;

        if (!SimpleJetpacks.isWearingJetpack(player)){
            return;
        }

        // check if the reason we are toggling is because we landed
        // this avoids glitching into the ground after crash-landing
        if (player.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR) {
            player.setSneaking(false);
            SimpleJetpacks.setJetpacking(player, false);
            return;
        }
        if (SimpleJetpacks.isJetpacking(player)) {
            player.setGliding(true);
            event.setCancelled(true);
        }
    }
}
