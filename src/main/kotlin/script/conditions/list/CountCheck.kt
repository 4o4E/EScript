package top.e404.escript.script.conditions.list

import org.bukkit.entity.Player
import top.e404.escript.annotation.ConditionSign
import top.e404.escript.config.CustomCounter
import top.e404.escript.hook.PlaceholderAPIHook.papi
import top.e404.escript.script.ExecutionException
import top.e404.escript.script.ParseException
import top.e404.escript.script.conditions.Condition
import top.e404.escript.script.conditions.ConditionParser

/**
 * 指定的计数器数值比较, 左侧计数器名, 右侧数值
 */
@ConditionSign
object CountCheck : ConditionParser {
    override val headRegex = Regex("(?i)checkCount|countCheck")
    override fun parse(content: Any?): Condition {
        val str = content.toString()
        when {
            str.contains("==") -> {
                val i = str.indexOf("==")
                val first = str.substring(0, i).trim()
                val second = str.substring(i + 2).trim()
                return Condition { p: Player -> CustomCounter[first.papi(p), p] == second.papi(p).toLongOrNull() }
            }

            str.contains("!=") -> {
                val i = str.indexOf("!=")
                val first = str.substring(0, i).trim()
                val second = str.substring(i + 2).trim()
                return Condition { p: Player -> CustomCounter[first.papi(p), p] != second.papi(p).toLongOrNull() }
            }

            str.contains(">=") -> {
                val i = str.indexOf(">=")
                val first = str.substring(0, i).trim()
                val second = str.substring(i + 2).trim()
                return Condition { p: Player ->
                    val papi = second.papi(p)
                    val value = try {
                        papi.toLong()
                    } catch (e: Exception) {
                        throw ExecutionException("${papi}不是有效数值", e)
                    }
                    CustomCounter[first.papi(p), p] >= value
                }
            }

            str.contains("<=") -> {
                val i = str.indexOf("<=")
                val first = str.substring(0, i).trim()
                val second = str.substring(i + 2).trim()
                return Condition { p: Player ->
                    val papi = second.papi(p)
                    val value = try {
                        papi.toLong()
                    } catch (e: Exception) {
                        throw ExecutionException("${papi}不是有效数值", e)
                    }
                    CustomCounter[first.papi(p), p] <= value
                }
            }

            str.contains(">") -> {
                val i = str.indexOf(">")
                val first = str.substring(0, i).trim()
                val second = str.substring(i + 2).trim()
                return Condition { p: Player ->
                    val papi = second.papi(p)
                    val value = try {
                        papi.toLong()
                    } catch (e: Exception) {
                        throw ExecutionException("${papi}不是有效数值", e)
                    }
                    CustomCounter[first.papi(p), p] > value
                }
            }

            str.contains("<") -> {
                val i = str.indexOf("<")
                val first = str.substring(0, i).trim()
                val second = str.substring(i + 2).trim()
                return Condition { p: Player ->
                    val papi = second.papi(p)
                    val value = try {
                        papi.toLong()
                    } catch (e: Exception) {
                        throw ExecutionException("${papi}不是有效数值", e)
                    }
                    CustomCounter[first.papi(p), p] < value
                }
            }

            else -> throw ParseException("错误的check格式$str")
        }
    }
}