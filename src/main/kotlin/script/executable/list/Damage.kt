package top.e404.escript.script.executable.list

import top.e404.escript.annotation.ExecutionSign
import top.e404.escript.script.ExecutionException
import top.e404.escript.script.ParseException
import top.e404.escript.script.executable.Execution
import top.e404.escript.script.executable.ExecutionParser

@ExecutionSign
object Damage : ExecutionParser {
    override val headRegex = Regex("(?i)damage")

    override fun parse(content: Any?): Execution {
        if (content == null) throw ParseException("damage的内容不可为空")
        val s = content.toString()
        try {
            val value = s.toDouble()
            return Execution { it.damage(value) }
        } catch (t: Throwable) {
            throw ExecutionException("解析delay时出现异常, 脚本: `$s`", t)
        }
    }
}