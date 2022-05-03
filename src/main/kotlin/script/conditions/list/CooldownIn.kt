package top.e404.escript.script.conditions.list

import top.e404.escript.annotation.ConditionSign
import top.e404.escript.config.CustomCooldown
import top.e404.escript.script.ParseException
import top.e404.escript.script.conditions.Condition
import top.e404.escript.script.conditions.ConditionParser

@ConditionSign
object CooldownIn : ConditionParser {
    override val headRegex = Regex("(?i)in(cd|cooldown)")
    override fun parse(content: Any?): Condition {
        if (content == null) throw ParseException("cd检查的名字不可为空")
        val name = content.toString()
        return Condition { p ->
            val cd = CustomCooldown.getPlayerCooldownStamp(p, name) ?: return@Condition true
            cd <= System.currentTimeMillis()
        }
    }
}