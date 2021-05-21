package io.github.trainb0y1.simplejetpacks;

import io.github.trainb0y1.simplejetpacks.events.*;
import io.github.trainb0y1.simplejetpacks.commands.JetpackCommands;
import io.github.trainb0y1.simplejetpacks.items.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;


public class SimpleJetpacks extends JavaPlugin {
    private static SimpleJetpacks plugin;
    public final int maxFuel = 1000;
    @Override
    public void onEnable() {
        plugin = this;
        ItemManager.createJetpack(this);
        //getServer().getPluginManager().registerEvents(new JetpackEvents(), this);
        getServer().getPluginManager().registerEvents(new RefuelEventListener(), this);
        getServer().getPluginManager().registerEvents(new JetpackFlyKickListener(), this);
        getServer().getPluginManager().registerEvents(new JetpackBreakListener(), this);
        getServer().getPluginManager().registerEvents(new JetpackToggleListener(), this);
        getServer().getPluginManager().registerEvents(new JetpackFlyingListener(), this);

        getCommand("jetpack").setExecutor(new JetpackCommands());
        getServer().getConsoleSender().sendMessage(ChatColor.BLUE+"[SimpleJetpacks]: Plugin Enabled");
    }
    @Override
    public void onDisable() {
        // Send disable message
        getServer().getConsoleSender().sendMessage(ChatColor.BLUE+"[SimpleJetpacks]: Plugin Disabled");

    }
    public static SimpleJetpacks getPlugin(){
        return plugin;
    }
    public static boolean isJetpack(ItemMeta meta){
        // Check if the meta has the jetpack data
        PersistentDataContainer data = meta.getPersistentDataContainer();
        if (data.get(new NamespacedKey(SimpleJetpacks.getPlugin(),"jetpack"), PersistentDataType.INTEGER)!= null){
            return  true;
        }
        else {return false;}
    }

}
