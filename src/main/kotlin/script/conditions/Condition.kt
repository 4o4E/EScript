package top.e404.escript.script.conditions

import org.bukkit.entity.Player

fun interface Condition {
    fun invoke(p: Player): Boolean
}