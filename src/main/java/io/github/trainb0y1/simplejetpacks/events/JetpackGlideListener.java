package io.github.trainb0y1.simplejetpacks.events;

import io.github.trainb0y1.simplejetpacks.SimpleJetpacks;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


public class JetpackGlideListener implements Listener {
    @EventHandler
    public void onGlideToggle(EntityToggleGlideEvent event) {
        if (!SimpleJetpacks.oldMotion){
            Entity entity = event.getEntity();
            if (entity instanceof Player) {
                Player player = (Player) entity;
                PersistentDataContainer playerData = player.getPersistentDataContainer();
                if (playerData.get(new NamespacedKey(SimpleJetpacks.getPlugin(), "jetpacking"), PersistentDataType.INTEGER) == 1) {
                    player.setGliding(true);
                    event.setCancelled(true);
                }
            }
        }
    }
}
