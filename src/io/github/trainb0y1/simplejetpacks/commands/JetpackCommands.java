package io.github.trainb0y1.simplejetpacks.commands;


import io.github.trainb0y1.simplejetpacks.SimpleJetpacks;
import io.github.trainb0y1.simplejetpacks.items.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JetpackCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("simplejetpacks")){
            SimpleJetpacks.getPlugin().getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "Found command");
            if (args.length == 0){
                return false; // Will return the plugin.yml usage message
            }
            // Jetpack give command
            if (args[0].equalsIgnoreCase("give")){
                SimpleJetpacks.getPlugin().getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "Found give command");
                // /simplejetpacks give
                if (!(sender instanceof Player)) {
                    sender.sendMessage("Only players can use this command!");
                    return true;
                }
                if (!sender.hasPermission("simplejetpacks.give")){
                    sender.sendMessage(ChatColor.RED + "You do not have the permission simplejetpacks.give!");
                    return true;
                }
                Player player = (Player) sender;
                player.getInventory().addItem(ItemManager.jetpack);
            }

            // Config file reload command
            if (args[0].equalsIgnoreCase("reload")){
                SimpleJetpacks.getPlugin().getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "Found reload command");
                if (!sender.hasPermission("simplejetpacks.reload")){
                    sender.sendMessage(ChatColor.RED + "You do not have the permission simplejetpacks.reload!");
                    return true;
                }
                SimpleJetpacks.getPlugin().reloadConfig(); // reload the config
                sender.sendMessage(ChatColor.GREEN+"[SimpleJetpacks] Config reloaded");
            }
        }
        return true;
    }
}
