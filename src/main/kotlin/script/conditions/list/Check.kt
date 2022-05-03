package top.e404.escript.script.conditions.list

import org.bukkit.entity.Player
import top.e404.escript.annotation.ConditionSign
import top.e404.escript.hook.PlaceholderAPIHook.papi
import top.e404.escript.script.ParseException
import top.e404.escript.script.conditions.Condition
import top.e404.escript.script.conditions.ConditionParser

@ConditionSign
object Check : ConditionParser {
    override val headRegex = Regex("(?i)check|condition")
    override fun parse(content: Any?): Condition {
        if (content == null) throw ParseException("check的内容不可为空")
        val str = content.toString()
        when {
            str.contains("==") -> {
                val i = str.indexOf("==")
                val first = str.substring(0, i).trim()
                val second = str.substring(i + 2).trim()
                return Condition { p: Player ->
                    val f = first.papi(p)
                    val s = second.papi(p)
                    val fd = f.toDoubleOrNull()
                    val sd = s.toDoubleOrNull()
                    if (fd != null && sd != null) fd == sd
                    else f == s
                }
            }
            str.contains("!=") -> {
                val i = str.indexOf("!=")
                val first = str.substring(0, i).trim()
                val second = str.substring(i + 2).trim()
                return Condition { p: Player ->
                    val f = first.papi(p)
                    val s = second.papi(p)
                    val fd = f.toDoubleOrNull()
                    val sd = s.toDoubleOrNull()
                    if (fd != null && sd != null) fd != sd
                    else f != s
                }
            }
            str.contains(">=") -> {
                val i = str.indexOf(">=")
                val first = str.substring(0, i).trim()
                val second = str.substring(i + 2).trim()
                return Condition { p: Player ->
                    val f = first.papi(p)
                    val s = second.papi(p)
                    val fd = f.toDoubleOrNull()
                    val sd = s.toDoubleOrNull()
                    if (fd != null && sd != null) fd >= sd
                    else f >= s
                }
            }
            str.contains("<=") -> {
                val i = str.indexOf("<=")
                val first = str.substring(0, i).trim()
                val second = str.substring(i + 2).trim()
                return Condition { p: Player ->
                    val f = first.papi(p)
                    val s = second.papi(p)
                    val fd = f.toDoubleOrNull()
                    val sd = s.toDoubleOrNull()
                    if (fd != null && sd != null) fd <= sd
                    else f <= s
                }
            }
            str.contains(">") -> {
                val i = str.indexOf(">")
                val first = str.substring(0, i).trim()
                val second = str.substring(i + 2).trim()
                return Condition { p: Player ->
                    val f = first.papi(p)
                    val s = second.papi(p)
                    val fd = f.toDoubleOrNull()
                    val sd = s.toDoubleOrNull()
                    if (fd != null && sd != null) fd > sd
                    else f > s
                }
            }
            str.contains("<") -> {
                val i = str.indexOf("<")
                val first = str.substring(0, i).trim()
                val second = str.substring(i + 2).trim()
                return Condition { p: Player ->
                    val f = first.papi(p)
                    val s = second.papi(p)
                    val fd = f.toDoubleOrNull()
                    val sd = s.toDoubleOrNull()
                    if (fd != null && sd != null) fd < sd
                    else f < s
                }
            }
            else -> throw ParseException("错误的check格式$str")
        }
    }
}