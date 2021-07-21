package io.github.trainb0y1.simplejetpacks.commands;


import io.github.trainb0y1.simplejetpacks.SimpleJetpacks;
import io.github.trainb0y1.simplejetpacks.items.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class JetpackCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("simplejetpacks")){
            if (args.length == 0){
                return false; // Will return the plugin.yml usage message
            }
            // Jetpack give command
            if (args[0].equalsIgnoreCase("give")){
                // /simplejetpacks give
                if (!(sender instanceof Player)) {
                    sender.sendMessage("Only players can use this command!");
                    return true;
                }
                if (!sender.hasPermission("simplejetpacks.give")){
                    sender.sendMessage(ChatColor.RED + "You do not have the permission simplejetpacks.give!");
                    return true;
                }
                if (args.length == 1){ //If there's only one argument, there isn't two!
                    sender.sendMessage(ChatColor.RED + "No base chestplate specified! Available Jetpacks:");
                    for (ItemStack jetpack: ItemManager.jetpacks){
                        sender.sendMessage(ChatColor.RED + "    " + jetpack.getType().toString());
                    }
                    return true;
                }
                Player player = (Player) sender;
                ItemStack pack = ItemManager.getJetpack(Material.getMaterial(args[1].toUpperCase()));
                if (pack == null){
                    player.sendMessage(ChatColor.RED+"There is no jetpack defined with that base item. \nAvailable Jetpacks:");
                    for (ItemStack jetpack: ItemManager.jetpacks){
                        player.sendMessage(ChatColor.RED + "    " + jetpack.getType().toString());
                    }
                }
                else {
                    player.getInventory().addItem(pack);
                }
                return true;
            }

            // Config file reload command
            if (args[0].equalsIgnoreCase("reload")){
                SimpleJetpacks.getPlugin().getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "Found reload command");
                if (!sender.hasPermission("simplejetpacks.reload")){
                    sender.sendMessage(ChatColor.RED + "You do not have the permission simplejetpacks.reload!");
                    return true;
                }
                SimpleJetpacks.getPlugin().reloadConfig(); // reload the config
                sender.sendMessage(ChatColor.GREEN+"[SimpleJetpacks] Config reloaded! If you defined a new jetpack, a server restart is required to apply changes.");
            }
        }
        return true;
    }
}
