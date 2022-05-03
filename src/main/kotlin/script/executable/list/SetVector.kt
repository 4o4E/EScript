package top.e404.escript.script.executable.list

import org.bukkit.entity.Player
import top.e404.escript.annotation.ExecutionSign
import top.e404.escript.hook.PlaceholderAPIHook.papi
import top.e404.escript.script.ExecutionException
import top.e404.escript.script.ParseException
import top.e404.escript.script.executable.Execution
import top.e404.escript.script.executable.ExecutionParser
import top.e404.escript.util.color

@ExecutionSign
object SetVector : ExecutionParser {
    override val headRegex = Regex("(?i)vector")

    override fun parse(content: Any?): Execution {
        if (content == null) throw ParseException("vector的内容不可为空")
        if (content is Map<*, *>) {
            val map = content.entries.associate { (k, v) -> k.toString().lowercase() to v.toString() }
            val x = map["x"]
            val y = map["y"]
            val z = map["z"]
            return Execution { p: Player ->
                val v = p.velocity
                if (x != null) when {
                    x.startsWith("+") -> v.x += x.removePrefix("+").toDouble()
                    x.startsWith("-") -> v.x -= x.removePrefix("-").toDouble()
                    else -> v.x = x.toDouble()
                }
                if (y != null) when {
                    y.startsWith("+") -> v.y += y.removePrefix("+").toDouble()
                    y.startsWith("-") -> v.y -= y.removePrefix("-").toDouble()
                    else -> v.y = y.toDouble()
                }
                if (z != null) when {
                    z.startsWith("+") -> v.z += z.removePrefix("+").toDouble()
                    z.startsWith("-") -> v.z -= z.removePrefix("-").toDouble()
                    else -> v.z = z.toDouble()
                }
                p.velocity = v
            }
        }
        val s = content.toString()
        return Execution { p: Player ->
            try {
                p.sendMessage(s.papi(p).color())
            } catch (t: Throwable) {
                throw ExecutionException("执行向玩家发送消息的脚本时出现异常, 脚本: `$content`", t)
            }
        }
    }
}