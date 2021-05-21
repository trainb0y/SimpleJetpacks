package io.github.trainb0y1.simplejetpacks.events;

import io.github.trainb0y1.simplejetpacks.SimpleJetpacks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;


public class JetpackFlyKickListener implements Listener {

    @EventHandler
    public void onKicked(PlayerKickEvent event) {
        // Prevent fly kicking while jetpacking
        Player player = event.getPlayer();
        if (event.getPlayer().getInventory().getChestplate() != null) {
            if (SimpleJetpacks.isJetpack(player.getInventory().getChestplate().getItemMeta())) {
                if (event.getReason().contains("Flying")) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
