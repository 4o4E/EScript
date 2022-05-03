package top.e404.escript.script.executable.list

import org.bukkit.entity.Player
import top.e404.escript.annotation.ExecutionSign
import top.e404.escript.hook.PlaceholderAPIHook.papi
import top.e404.escript.script.ExecutionException
import top.e404.escript.script.ParseException
import top.e404.escript.script.executable.Execution
import top.e404.escript.script.executable.ExecutionParser

@ExecutionSign
object Level : ExecutionParser {
    override val headRegex = Regex("(?i)level|lvl|exp|xp")

    private val l = listOf('L', 'l')

    override fun parse(content: Any?): Execution {
        if (content == null) throw ParseException("level的内容不可为空")
        val s = content.toString()
        return if (s.startsWith("-")) Execution { p: Player ->
            try {
                if (s.last() in l) p.giveExpLevels(-s.removePrefix("-").papi(p).toInt())
                else p.giveExp(-s.removePrefix("-").papi(p).toInt())
            } catch (t: Throwable) {
                throw ExecutionException("设置玩家等级时出现异常, 脚本: `$content`", t)
            }
        } else Execution { p: Player ->
            try {
                if (s.last() in l) p.giveExpLevels(s.papi(p).toInt())
                else p.giveExp(s.papi(p).toInt())
            } catch (t: Throwable) {
                throw ExecutionException("设置玩家等级时出现异常, 脚本: `$content`", t)
            }
        }
    }
}