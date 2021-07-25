package io.github.trainb0y1.simplejetpacks.events;

import io.github.trainb0y1.simplejetpacks.SimpleJetpacks;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


public class JetpackGlideListener implements Listener {
    /*
    Keep the player in glide mode while jetpacking
     */
    @EventHandler
    public void onGlideToggle(EntityToggleGlideEvent event) {
        if (!SimpleJetpacks.oldMotion){
            Entity entity = event.getEntity();
            if (entity instanceof Player) {
                Player player = (Player) entity;
                if (SimpleJetpacks.isJetpacking(player)){
                    player.setGliding(true);
                    event.setCancelled(true);
                }
            }
        }
    }
}
