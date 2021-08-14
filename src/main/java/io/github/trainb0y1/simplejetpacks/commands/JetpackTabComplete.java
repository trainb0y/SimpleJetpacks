package io.github.trainb0y1.simplejetpacks.commands;

import io.github.trainb0y1.simplejetpacks.SimpleJetpacks;
import io.github.trainb0y1.simplejetpacks.items.ItemManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class JetpackTabComplete implements TabCompleter {
    /*
    Command argument autocomplete
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args){
        if (args.length == 1){
            List<String> subcommands = new ArrayList<String>();
            if(sender.hasPermission("simplejetpacks.reload")){ subcommands.add("reload"); }
            if(sender.hasPermission("simplejetpacks.give")){ subcommands.add("give"); }
            return subcommands;

        }
        if (args.length == 2){
            if (args[0].equalsIgnoreCase("give") && sender.hasPermission("simplejetpacks.give")){
                // We need to send a list of players
                List<String> playerNames = new ArrayList<String>();
                Player[] players = new Player[SimpleJetpacks.getPlugin().getServer().getOnlinePlayers().size()];
                SimpleJetpacks.getPlugin().getServer().getOnlinePlayers().toArray(players);
                for (Player player : players) {
                    playerNames.add(player.getName());
                }
                return playerNames;
            }
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("give") && sender.hasPermission("simplejetpacks.give")){
                // We need to send a list of jetpack types
                List<String> jetpackTypes = new ArrayList<String>();
                for (ItemStack jetpack: ItemManager.jetpacks){
                    jetpackTypes.add(jetpack.getType().toString());
                }
                return jetpackTypes;
            }
        }
        return null;
    }
}
