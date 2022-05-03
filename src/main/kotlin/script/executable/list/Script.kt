package top.e404.escript.script.executable.list

import org.bukkit.entity.Player
import top.e404.escript.annotation.ExecutionSign
import top.e404.escript.hook.PlaceholderAPIHook.papi
import top.e404.escript.script.ExecutionException
import top.e404.escript.script.ParseException
import top.e404.escript.script.ScriptManager
import top.e404.escript.script.executable.Execution
import top.e404.escript.script.executable.ExecutionParser

@ExecutionSign
object Script : ExecutionParser {
    override val headRegex = Regex("(?i)script")

    override fun parse(content: Any?): Execution {
        if (content == null) throw ParseException("script的内容不可为空")
        val name = content.toString()
        return Execution { p: Player ->
            val s = name.papi(p)
            val script = ScriptManager[s] ?: throw ExecutionException("不存在的脚本: `$s`")
            script.invoke(p)
        }
    }
}