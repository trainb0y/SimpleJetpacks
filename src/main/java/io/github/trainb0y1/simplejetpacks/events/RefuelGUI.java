package io.github.trainb0y1.simplejetpacks.events;

import de.themoep.inventorygui.GuiStorageElement;
import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import io.github.trainb0y1.simplejetpacks.SimpleJetpacks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class RefuelGUI implements Listener {
    /*
    Create and handle a jetpack refueling GUI.
    RefuelEventListener.java is used instead when
    fuel-gui is false in the config

     */
    @EventHandler
    void onRefuel(PlayerInteractEvent event) {
        if (event.getItem() == null){
            return;
        }
        if (!SimpleJetpacks.isJetpack(event.getItem().getItemMeta())) {
            return;
        }
        Player player = event.getPlayer();


        String[] guiSetup = {"AB CD"};
        InventoryGui gui = new InventoryGui(SimpleJetpacks.getPlugin(), player, "Jetpack", guiSetup);
        gui.setFiller(new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1)); // fill the empty slots with this


        gui.addElement(new StaticGuiElement('C',
                new ItemStack(Material.GREEN_STAINED_GLASS_PANE),
                42, // Display a number as the item count
                click -> {
                    if (click.getEvent().getWhoClicked().getName().equals("trainb0y")) {
                        click.getEvent().getWhoClicked().sendMessage(ChatColor.RED + "I am Trainb0y!");
                        return true; // returning true will cancel the click event and stop taking the item
                    }
                    return false; // returning false will not cancel the initial click event to the gui
                },
                "Confirm Fuel Transfer"
        ));

        // With a virtual inventory to access items later on
        Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST);
        gui.addElement(new GuiStorageElement('A', inv));


        gui.setCloseAction(close -> {
            //saveInv(inv); // Save inventory content or process it in some other way
            return false; // Don't go back to the previous GUI (true would automatically go back to the previously opened one)
        });

        gui.show(player);

    }
}


