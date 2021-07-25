package io.github.trainb0y1.simplejetpacks.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class KotlinCommandTest : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        sender.sendMessage("hey it works!")
        return true
    }
}