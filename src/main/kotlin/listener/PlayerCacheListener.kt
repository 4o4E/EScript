package top.e404.escript.listener

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import top.e404.escript.EScript
import top.e404.escript.config.PlayerCache

object PlayerCacheListener : Listener {
    fun register() = Bukkit.getPluginManager().registerEvents(this, EScript.instance)

    @EventHandler
    fun PlayerJoinEvent.onJoin() {
        PlayerCache.update(player)
    }
}