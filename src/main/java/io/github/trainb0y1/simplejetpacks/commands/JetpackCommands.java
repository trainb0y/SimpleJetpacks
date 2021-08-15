package io.github.trainb0y1.simplejetpacks.commands;


import io.github.trainb0y1.simplejetpacks.SimpleJetpacks;
import io.github.trainb0y1.simplejetpacks.items.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class JetpackCommands implements CommandExecutor {
    /*
    Handles all jetpack-related commands
     */

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("simplejetpacks")) {
            if (args.length == 0) {
                return false; // Will return the plugin.yml usage message
            }

            if (args[0].equalsIgnoreCase("give")) {
                // /simplejetpacks give
                if (!sender.hasPermission("simplejetpacks.give")) {
                    sender.sendMessage(ChatColor.RED + "You do not have the permission simplejetpacks.give!");
                    return true;
                }
                if (args.length != 3) {
                    sender.sendMessage(ChatColor.RED + "Incorrect number of arguments for Give! \nUsage: /simplejetpacks give <player> <type>");
                    return true;
                }

                // get the specified player
                Player player = Bukkit.getPlayer(args[1]);
                if (player == null) {
                    sender.sendMessage(ChatColor.RED + "[SimpleJetpacks] " + args[1] + " is not a valid player!");
                    return true;
                }

                // get the specified jetpack type
                ItemStack pack = ItemManager.getJetpack(Material.getMaterial(args[2].toUpperCase()));
                if (pack == null) {
                    sender.sendMessage(ChatColor.RED + "[SimpleJetpacks] " + args[2] + " is not a valid jetpack type.\n Available Jetpacks:");
                    for (ItemStack jetpack : ItemManager.jetpacks) {
                        sender.sendMessage(ChatColor.RED + "    " + jetpack.getType());
                    }
                    return true;
                }

                player.getInventory().addItem(pack);
                sender.sendMessage(ChatColor.GREEN + "[SimpleJetpacks] Gave 1 jetpack of type " + args[2] + " to player " + args[1]);
                return true;
            }


            // Config file reload command
            if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("simplejetpacks.reload")) {
                    sender.sendMessage(ChatColor.RED + "You do not have the permission simplejetpacks.reload!");
                    return true;
                }
                SimpleJetpacks.getPlugin().reloadConfig(); // reload the config
                SimpleJetpacks.oldMotion = SimpleJetpacks.getPlugin().getConfig().getBoolean("old-motion");
                sender.sendMessage(ChatColor.GREEN + "[SimpleJetpacks] Config reloaded! If you defined a new jetpack, a server restart is required to apply changes.");
            }
        }
        return true;
    }
}
