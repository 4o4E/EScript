package top.e404.escript.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import top.e404.escript.config.CommandTrigger
import top.e404.escript.util.warn

object PlayerCommandListener : IListener {
    @EventHandler
    fun PlayerCommandPreprocessEvent.onEvent() {
        try {
            CommandTrigger.onEvent(this)
        } catch (t: Throwable) {
            warn("监听玩家执行指令时出现异常, 玩家: ${player.name}, 指令: $message", t)
        }
    }
}