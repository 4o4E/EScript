package top.e404.escript.script.executable.list

import org.bukkit.entity.Player
import top.e404.escript.annotation.ExecutionSign
import top.e404.escript.hook.PlaceholderAPIHook.papi
import top.e404.escript.script.ExecutionException
import top.e404.escript.script.ParseException
import top.e404.escript.script.executable.Execution
import top.e404.escript.script.executable.ExecutionParser

@ExecutionSign
object WalkSpeed : ExecutionParser {
    override val headRegex = Regex("(?i)walkSpeed")

    override fun parse(content: Any?): Execution {
        if (content == null) throw ParseException("walkSpeed的内容不可为空")
        val s = content.toString()
        return when {
            s.startsWith("+") -> Execution { p: Player ->
                try {
                    p.walkSpeed += s.papi(p).toFloat()
                } catch (t: Throwable) {
                    throw ExecutionException("设置玩家行走速度时出现异常, 脚本: `$content`", t)
                }
            }
            s.startsWith("-") -> Execution { p: Player ->
                try {
                    p.walkSpeed -= s.papi(p).toFloat()
                } catch (t: Throwable) {
                    throw ExecutionException("设置玩家行走速度时出现异常, 脚本: `$content`", t)
                }
            }
            else -> Execution { p: Player ->
                try {
                    p.walkSpeed = s.papi(p).toFloat()
                } catch (t: Throwable) {
                    throw ExecutionException("设置玩家行走速度时出现异常, 脚本: `$content`", t)
                }
            }
        }
    }
}