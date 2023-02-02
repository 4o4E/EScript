package top.e404.escript.script.conditions.list

import top.e404.escript.annotation.ConditionSign
import top.e404.escript.config.CustomCounter
import top.e404.escript.script.ParseException
import top.e404.escript.script.conditions.Condition
import top.e404.escript.script.conditions.ConditionParser
import top.e404.escript.util.asIntRange
import top.e404.escript.util.format

/**
 * 指定的计数器数值在范围外
 */
@ConditionSign
object CountOut : ConditionParser {
    override val headRegex = Regex("(?i)outCount|countOut")
    override fun parse(content: Any?): Condition {
        if (content == null) throw ParseException("countOun检查不可为空")
        if (content !is Map<*, *>) throw ParseException("countOun检查格式错误")
        val map = content.format()
        val rangeString = map["range"]?.toString() ?: throw ParseException("countOun检查range不可为空")
        val name = map["name"]?.toString() ?: throw ParseException("countOun检查name不可为空")
        val range = rangeString.asIntRange()
        return Condition { p ->
            return@Condition CustomCounter[name, p] !in range
        }
    }
}