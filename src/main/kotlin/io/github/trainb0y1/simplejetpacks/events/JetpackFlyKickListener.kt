package io.github.trainb0y1.simplejetpacks.events

import io.github.trainb0y1.simplejetpacks.SimpleJetpacks
import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerKickEvent

class JetpackFlyKickListener : Listener {
    /*
    Prevent the player from getting fly-kicked while jetpacking
     */
    @EventHandler
    fun onKicked(event: PlayerKickEvent) {
        // Prevent fly kicking while jetpacking
        if (SimpleJetpacks.isWearingJetpack(event.player)) {
            if (event.reason().contains(Component.text("Flying"))) {
                event.isCancelled = true
            }
        }
    }
}