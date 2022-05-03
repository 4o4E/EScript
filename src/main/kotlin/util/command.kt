package top.e404.escript.util

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

fun String.execAsCommand(sender: CommandSender = Bukkit.getConsoleSender()) {
    Bukkit.dispatchCommand(sender, this)
}