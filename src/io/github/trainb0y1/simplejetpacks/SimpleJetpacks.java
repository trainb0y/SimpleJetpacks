package io.github.trainb0y1.simplejetpacks;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleJetpacks extends JavaPlugin {
    @Override
    public void onEnable() {
        // Send enable message
        getServer().getConsoleSender().sendMessage(ChatColor.BLUE+"[SimpleJetpacks]: Plugin Enabled");
        JetpackItem.init();
        getServer().getPluginManager().registerEvents(new JetpackEvents(this), this);
        getCommand("jetpack").setExecutor(new JetpackCommands());
    }
    @Override
    public void onDisable() {
        // Send disable message
        getServer().getConsoleSender().sendMessage(ChatColor.BLUE+"[SimpleJetpacks]: Plugin Disabled");

    }

}
