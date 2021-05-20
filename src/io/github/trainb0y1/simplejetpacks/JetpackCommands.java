package io.github.trainb0y1.simplejetpacks;

import io.github.trainb0y1.simplejetpacks.JetpackItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JetpackCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("jetpack")) {
            player.getInventory().addItem(JetpackItem.jetpack);
        }
        return true;
    }

}