package top.e404.escript.script.conditions.list

import org.bukkit.entity.Player
import top.e404.escript.annotation.ConditionSign
import top.e404.escript.script.ParseException
import top.e404.escript.script.conditions.Condition
import top.e404.escript.script.conditions.ConditionParser
import top.e404.escript.util.asDoubleRange

@ConditionSign
object ZRangeIn : ConditionParser {
    override val headRegex = Regex("(?i)zInRange")
    override fun parse(content: Any?): Condition {
        if (content == null) throw ParseException("坐标检查的范围不可为空")
        val range = content.toString().asDoubleRange()
        return Condition { p: Player ->
            p.location.z in range
        }
    }
}