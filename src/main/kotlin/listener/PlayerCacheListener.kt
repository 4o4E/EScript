package top.e404.escript.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import top.e404.escript.config.PlayerCache

object PlayerCacheListener : IListener {
    @EventHandler
    fun PlayerJoinEvent.onJoin() {
        PlayerCache.update(player)
    }
}