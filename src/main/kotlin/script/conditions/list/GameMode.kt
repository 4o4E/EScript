package top.e404.escript.script.conditions.list

import top.e404.escript.annotation.ConditionSign
import top.e404.escript.script.ParseException
import top.e404.escript.script.conditions.Condition
import top.e404.escript.script.conditions.ConditionParser

@ConditionSign
object GameMode : ConditionParser {
    private val splitRegex = Regex("[,;\\s.]")

    override val headRegex = Regex("(?i)gameMode|gm")
    override fun parse(content: Any?): Condition {
        if (content == null) throw ParseException("游戏模式检查的名字不可为空")
        val list = content.toString().split(splitRegex).map { it.uppercase() }
        return Condition {
            it.gameMode.name in list
        }
    }
}