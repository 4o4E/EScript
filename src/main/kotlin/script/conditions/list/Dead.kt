package top.e404.escript.script.conditions.list

import top.e404.escript.annotation.ConditionSign
import top.e404.escript.script.conditions.Condition
import top.e404.escript.script.conditions.ConditionParser

@ConditionSign
object Dead : ConditionParser {
    override val headRegex = Regex("(?i)isDead|isNotAlive")
    override fun parse(content: Any?) = Condition { it.isDead }
}