package top.e404.escript.script.conditions.list

import org.bukkit.entity.Player
import top.e404.escript.annotation.ConditionSign
import top.e404.escript.script.ParseException
import top.e404.escript.script.conditions.Condition
import top.e404.escript.script.conditions.ConditionParser

@ConditionSign
object WorldNotIn : ConditionParser {
    override val headRegex = Regex("(?i)(notIn|out)World")
    override fun parse(content: Any?): Condition {
        if (content == null) throw ParseException("世界检查的世界不可为空")
        return if (content is Collection<*>) content.let { worlds ->
            if (worlds.isEmpty()) Condition { true }
            else Condition { p: Player -> worlds.any { p.world.name != it } }
        } else Condition { p: Player ->
            p.world.name != content.toString()
        }
    }
}