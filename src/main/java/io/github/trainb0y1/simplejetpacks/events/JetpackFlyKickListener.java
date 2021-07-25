package io.github.trainb0y1.simplejetpacks.events;

import io.github.trainb0y1.simplejetpacks.SimpleJetpacks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;


public class JetpackFlyKickListener implements Listener {
    /*
    Prevent the player from getting fly-kicked while jetpacking
     */
    @EventHandler
    public void onKicked(PlayerKickEvent event) {
        // Prevent fly kicking while jetpacking
        Player player = event.getPlayer();
        if (SimpleJetpacks.isWearingJetpack(event.getPlayer())) {
            if (event.getReason().contains("Flying")) {
                event.setCancelled(true);
            }
        }
    }
}
