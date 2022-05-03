package top.e404.escript.script.conditions.list

import org.bukkit.entity.Player
import top.e404.escript.annotation.ConditionSign
import top.e404.escript.script.ParseException
import top.e404.escript.script.conditions.Condition
import top.e404.escript.script.conditions.ConditionParser

@ConditionSign
object PermHasNot : ConditionParser {
    override val headRegex = Regex("(?i)(hasNot|notHas|no)Perm")
    override fun parse(content: Any?): Condition {
        if (content == null) throw ParseException("权限检查的权限不可为空")
        return if (content is Collection<*>) content.let { perms ->
            if (perms.isEmpty()) Condition { true }
            else Condition { p: Player ->
                perms.all { !p.hasPermission(it.toString()) }
            }
        } else Condition { p: Player ->
            !p.hasPermission(content.toString())
        }
    }
}