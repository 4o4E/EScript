package top.e404.escript.script.executable.list

import org.bukkit.entity.Player
import top.e404.escript.annotation.ExecutionSign
import top.e404.escript.config.CustomCooldown.addCd
import top.e404.escript.config.CustomCooldown.resetCd
import top.e404.escript.config.CustomCooldown.setCd
import top.e404.escript.script.ParseException
import top.e404.escript.script.executable.Execution
import top.e404.escript.script.executable.ExecutionParser

@ExecutionSign
object CustomCoolDown : ExecutionParser {
    override val headRegex = Regex("(?i)cooldown|cd")

    override fun parse(content: Any?): Execution {
        if (content == null) throw ParseException("cooldown的内容不可为空")
        if (content is Map<*, *>) {
            val map = content.entries.associate { (k, v) -> k.toString().lowercase() to v }
            val type = map["type"]?.toString() ?: "add"
            val name = map["name"]?.toString() ?: throw ParseException("cd名字不可为空")
            val value = map["value"]?.toString()?.toLongOrNull()
            return when (type.lowercase()) {
                "add" -> Execution { p: Player ->
                    p.addCd(name, value ?: throw ParseException("value应为整数"))
                }
                "set" -> Execution { p: Player ->
                    p.setCd(name, value ?: throw ParseException("value应为整数"))
                }
                "reset", "remove", "del", "delete" -> Execution { p: Player ->
                    p.resetCd(name)
                }
                else -> throw ParseException("未知的处理类型: $type")
            }
        }
        throw ParseException("脚本格式错误: $content")
    }
}