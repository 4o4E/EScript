package top.e404.escript.script.executable.list

import org.bukkit.entity.Player
import top.e404.escript.annotation.ExecutionSign
import top.e404.escript.hook.PlaceholderAPIHook.papi
import top.e404.escript.script.ExecutionException
import top.e404.escript.script.ParseException
import top.e404.escript.script.executable.Execution
import top.e404.escript.script.executable.ExecutionParser

@ExecutionSign
object Burn : ExecutionParser {
    override val headRegex = Regex("(?i)ignite|burn|fire")

    override fun parse(content: Any?): Execution {
        if (content == null) throw ParseException("ignite的内容不可为空")
        val s = content.toString()
        return when {
            s.startsWith("+") -> Execution { p: Player ->
                try {
                    val max = p.maxFireTicks
                    val t = p.maxFireTicks + s.removePrefix("+").papi(p).toInt()
                    p.fireTicks = t.coerceIn(0..max)
                } catch (t: Throwable) {
                    throw ExecutionException("设置玩家燃烧刻度时出现异常, 脚本: `$content`", t)
                }
            }
            s.startsWith("-") -> Execution { p: Player ->
                try {
                    val max = p.maxFireTicks
                    val t = p.fireTicks - s.removePrefix("-").papi(p).toInt()
                    p.fireTicks = t.coerceIn(0..max)
                } catch (t: Throwable) {
                    throw ExecutionException("设置玩家燃烧刻度时出现异常, 脚本: `$content`", t)
                }
            }
            else -> Execution { p: Player ->
                try {
                    p.fireTicks = s.removePrefix("-")
                        .papi(p)
                        .toInt()
                        .coerceIn(0..p.maxFireTicks)
                } catch (t: Throwable) {
                    throw ExecutionException("设置玩家燃烧刻度时出现异常, 脚本: `$content`", t)
                }
            }
        }
    }
}