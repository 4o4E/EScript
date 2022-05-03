package top.e404.escript.script.executable.list

import org.bukkit.entity.Player
import top.e404.escript.annotation.ExecutionSign
import top.e404.escript.script.ExecutionException
import top.e404.escript.script.ParseException
import top.e404.escript.script.executable.Execution
import top.e404.escript.script.executable.ExecutionParser

@ExecutionSign
object Delay : ExecutionParser {
    override val headRegex = Regex("(?i)delay|later")

    override fun parse(content: Any?): Execution {
        if (content == null) throw ParseException("delay的内容不可为空")
        val s = content.toString()
        try {
            return D(s.toLong())
        } catch (t: Throwable) {
            throw ExecutionException("解析delay时出现异常, 脚本: `$s`", t)
        }
    }

    class D(val duration: Long) : Execution {
        override fun invoke(p: Player) {}
    }
}