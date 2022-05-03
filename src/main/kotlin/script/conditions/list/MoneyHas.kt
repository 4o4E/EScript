package top.e404.escript.script.conditions.list

import org.bukkit.entity.Player
import top.e404.escript.annotation.ConditionSign
import top.e404.escript.hook.VaultHook
import top.e404.escript.script.ParseException
import top.e404.escript.script.conditions.Condition
import top.e404.escript.script.conditions.ConditionParser

@ConditionSign
object MoneyHas : ConditionParser {
    override val headRegex = Regex("(?i)has(bal|money|eco)")
    override fun parse(content: Any?): Condition {
        if (content == null) throw ParseException("余额检查的数量不可为空")
        val s = content.toString()
        val bal = try {
            s.toDouble()
        } catch (t: Throwable) {
            throw ParseException("${s}不是有效数字", t)
        }
        return Condition { p: Player ->
            VaultHook.getOrThrow().getBalance(p) >= bal
        }
    }
}