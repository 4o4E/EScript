package top.e404.escript.script.executable

import org.bukkit.entity.Player

fun interface Execution {
    fun invoke(p: Player)
}