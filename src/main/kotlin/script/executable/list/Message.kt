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
object Message : ExecutionParser {
    override val headRegex = Regex("(?i)message|msg")

    override fun parse(content: Any?): Execution {
        if (content == null) throw ParseException("message的内容不可为空")
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