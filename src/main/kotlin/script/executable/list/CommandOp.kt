package top.e404.escript.script.executable.list

import org.bukkit.entity.Player
import top.e404.escript.annotation.ExecutionSign
import top.e404.escript.hook.PlaceholderAPIHook.papi
import top.e404.escript.script.ExecutionException
import top.e404.escript.script.ParseException
import top.e404.escript.script.executable.Execution
import top.e404.escript.script.executable.ExecutionParser
import top.e404.escript.util.execAsCommand

@ExecutionSign
object CommandOp : ExecutionParser {
    override val headRegex = Regex("(?i)op")

    override fun parse(content: Any?): Execution {
        if (content == null) throw ParseException("command的内容不可为空")
        val s = content.toString()
        return Execution { p: Player ->
            val b = p.isOp
            try {
                if (!b) p.isOp = true
                s.papi(p).execAsCommand(p)
            } catch (t: Throwable) {
                throw ExecutionException("以OP身份执行指令时出现异常, 脚本: `$content`", t)
            } finally {
                if (!b) p.isOp = false
            }
        }
    }
}