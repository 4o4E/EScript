package top.e404.escript.listener

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import top.e404.escript.EScript

interface IListener : Listener {
    fun register() = Bukkit.getPluginManager().registerEvents(this, EScript.instance)
}