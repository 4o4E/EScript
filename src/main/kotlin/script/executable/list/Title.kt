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
object Title : ExecutionParser {
    override val headRegex = Regex("(?i)title")

    override fun parse(content: Any?): Execution {
        if (content == null) throw ParseException("title的内容不可为空")
        if (content is Map<*, *>) {
            val map = content.entries.associate { (k, v) -> k.toString().lowercase() to v }
            val title = map["title"]?.toString() ?: ""
            val subtitle = map["subtitle"]?.toString() ?: ""
            val fadeIn = map["fadein"]?.toString()?.toIntOrNull() ?: 20
            val stay = map["stay"]?.toString()?.toIntOrNull() ?: 20
            val fadeout = map["fadeout"]?.toString()?.toIntOrNull() ?: 20
            return Execution { p: Player ->
                try {
                    p.sendTitle(
                        title.papi(p).color(),
                        subtitle.papi(p).color(),
                        fadeIn, stay, fadeout
                    )
                } catch (t: Throwable) {
                    throw ExecutionException("向玩家发送标题时出现异常, 脚本: `$content`", t)
                }
            }
        }
        return Execution { p: Player ->
            try {
                p.sendTitle(
                    content.toString().papi(p).color(),
                    "", 20, 20, 20
                )
            } catch (t: Throwable) {
                throw ExecutionException("向玩家发送标题时出现异常, 脚本: `$content`", t)
            }
        }
    }
}