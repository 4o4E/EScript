package top.e404.escript.script.conditions.list

import org.bukkit.entity.Player
import top.e404.escript.annotation.ConditionSign
import top.e404.escript.script.conditions.Condition
import top.e404.escript.script.conditions.ConditionParser

@ConditionSign
object OpIs : ConditionParser {
    override val headRegex = Regex("(?i)isOp")
    override fun parse(content: Any?): Condition {
        return Condition { p: Player -> p.isOp }
    }
}