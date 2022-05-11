package top.e404.escript.script.conditions.list

import top.e404.escript.annotation.ConditionSign
import top.e404.escript.script.ParseException
import top.e404.escript.script.conditions.Condition
import top.e404.escript.script.conditions.ConditionParser
import top.e404.escript.util.asDoubleRange
import top.e404.escript.util.format
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

@ConditionSign
object FixDistanceOutRange : ConditionParser {
    override val headRegex = Regex("(?i)fix(distanceNotInRange|distanceOutRange)")
    override fun parse(content: Any?): Condition {
        if (content == null) throw ParseException("距离检查的世界不可为空")
        if (content !is Map<*, *>) throw ParseException("距离检查格式错误")
        val map = content.format()
        val rangeString = map["range"]?.toString() ?: throw ParseException("距离范围range不可为空")
        val range = rangeString.asDoubleRange()
        val xString = map["x"]?.toString() ?: throw ParseException("x不可为空")
        val yString = map["y"]?.toString() ?: throw ParseException("y不可为空")
        val zString = map["z"]?.toString() ?: throw ParseException("z不可为空")
        val x = xString.toDoubleOrNull() ?: throw ParseException("x格式错误")
        val y = yString.toDoubleOrNull() ?: throw ParseException("y格式错误")
        val z = zString.toDoubleOrNull() ?: throw ParseException("z格式错误")
        return Condition { p ->
            p.location.let { l ->
                return@let sqrt(
                    abs(l.x - x).pow(2)
                            + abs(l.y - y).pow(2)
                            + abs(l.z - z).pow(2)
                ) !in range
            }
        }
    }
}