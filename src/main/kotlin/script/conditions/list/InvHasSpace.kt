package top.e404.escript.script.conditions.list

import org.bukkit.Material
import org.bukkit.entity.Player
import top.e404.escript.annotation.ConditionSign
import top.e404.escript.script.ParseException
import top.e404.escript.script.conditions.Condition
import top.e404.escript.script.conditions.ConditionParser

@ConditionSign
object InvHasSpace : ConditionParser {
    override val headRegex = Regex("(?i)invHasSpace")
    override fun parse(content: Any?): Condition {
        if (content == null) throw ParseException("背包检查的数量不可为空")
        val s = content.toString()
        val count = try {
            s.toInt()
        } catch (t: Throwable) {
            throw ParseException("${s}不是有效数字", t)
        }
        return Condition { p: Player ->
            val empty = p.inventory.count { it == null || it.type == Material.AIR || it.amount == 0 }
            empty >= count
        }
    }
}