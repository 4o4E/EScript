package top.e404.escript.script.conditions.list

import org.bukkit.entity.Player
import top.e404.escript.annotation.ConditionSign
import top.e404.escript.script.conditions.Condition
import top.e404.escript.script.conditions.ConditionParser

@ConditionSign
object FlyDeny : ConditionParser {
    override val headRegex = Regex("(?i)(deny|cannot)(fly|flight)")
    override fun parse(content: Any?): Condition {
        return Condition { p: Player ->
            !p.allowFlight
        }
    }
}