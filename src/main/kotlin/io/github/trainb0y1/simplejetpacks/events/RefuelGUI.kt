package io.github.trainb0y1.simplejetpacks.events

import com.github.stefvanschie.inventoryframework.gui.type.FurnaceGui
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class RefuelGUI : Listener {
    /*
    Create and handle a jetpack refueling GUI.
    RefuelEventListener.java is used instead when
    fuel-gui is false in the config
     */
    @EventHandler
    fun onRefuel(event: PlayerInteractEvent){
        var gui = FurnaceGui("Jetpack");
    }
}