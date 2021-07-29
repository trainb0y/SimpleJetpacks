package io.github.trainb0y1.simplejetpacks.events;

import io.github.trainb0y1.simplejetpacks.SimpleJetpacks;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


public class JetpackToggleListener implements Listener {
    /*
    Enable/Disable jetpacking
     */

    @EventHandler
    public void onShiftPressed(PlayerToggleSneakEvent event) {
        // jetpack toggle
        Player player = event.getPlayer();

        PersistentDataContainer data = player.getPersistentDataContainer();
        if (data.get(new NamespacedKey(SimpleJetpacks.getPlugin(),"jetpacking"), PersistentDataType.INTEGER) == null) {
            data.set(new NamespacedKey(SimpleJetpacks.getPlugin(),"jetpacking"),PersistentDataType.INTEGER,0);
        } // Make sure they have the data

        if (SimpleJetpacks.isWearingJetpack(player)) {
            // if the player is in the air (they jumped) enable jetpack for them
            if (!SimpleJetpacks.isJetpacking(player) && player.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.AIR) { //&&
                //(player.isSneaking())){
                // player.issneaking prevents unshifting in midair to start, which leads you to only fall WHILE SHIFTING
                SimpleJetpacks.setJetpacking(player,true);
                player.setGliding(true);
            } else if (SimpleJetpacks.isJetpacking(player)) {
                SimpleJetpacks.setJetpacking(player,false);
                player.setGliding(false);
                player.setSneaking(false); // this should be redundant, but just in case...
            }
        }
    }
}
