package top.e404.escript.script.conditions.list

import org.bukkit.entity.Player
import top.e404.escript.annotation.ConditionSign
import top.e404.escript.script.ParseException
import top.e404.escript.script.conditions.Condition
import top.e404.escript.script.conditions.ConditionParser
import top.e404.escript.util.asIntRange

@ConditionSign
object HungerInRange : ConditionParser {
    override val headRegex = Regex("(?i)hungerInRange")
    override fun parse(content: Any?): Condition {
        if (content == null) throw ParseException("饥饿值检查的范围不可为空")
        val range = content.toString().asIntRange()
        return Condition { p: Player ->
            p.foodLevel in range
        }
    }
}