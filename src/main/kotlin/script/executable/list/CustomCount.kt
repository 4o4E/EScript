package top.e404.escript.script.executable.list

import org.bukkit.entity.Player
import top.e404.escript.annotation.ExecutionSign
import top.e404.escript.config.CustomCounter
import top.e404.escript.hook.PlaceholderAPIHook.papi
import top.e404.escript.script.ExecutionException
import top.e404.escript.script.ParseException
import top.e404.escript.script.executable.Execution
import top.e404.escript.script.executable.ExecutionParser

/**
 * 修改玩家的自定义计数器数值
 */
@ExecutionSign
object CustomCount : ExecutionParser {
    override val headRegex = Regex("(?i)count(er)?")

    override fun parse(content: Any?): Execution {
        if (content == null) throw ParseException("count的内容不可为空")
        if (content is Map<*, *>) {
            val map = content.entries.associate { (k, v) -> k.toString().lowercase() to v }
            val type = map["type"]?.toString() ?: "add"
            val name = map["name"]?.toString() ?: throw ParseException("计数器名字不可为空")
            val value = map["value"]?.toString()
            return when (type.lowercase()) {
                "add" -> Execution { p: Player ->
                    val papi = value?.papi(p) ?: throw ParseException("value不可为空")
                    val v = try {
                        papi.toLong()
                    } catch (e: Exception) {
                        throw ExecutionException("${papi}不是有效数值")
                    }
                    CustomCounter[name, p] += v
                }

                "times" -> Execution { p: Player ->
                    val papi = value?.papi(p) ?: throw ParseException("value不可为空")
                    val v = try {
                        papi.toLong()
                    } catch (e: Exception) {
                        throw ExecutionException("${papi}不是有效数值")
                    }
                    CustomCounter[name, p] *= v
                }

                "div" -> Execution { p: Player ->
                    val papi = value?.papi(p) ?: throw ParseException("value不可为空")
                    val v = try {
                        papi.toLong()
                    } catch (e: Exception) {
                        throw ExecutionException("${papi}不是有效数值")
                    }
                    CustomCounter[name, p] /= v
                }

                "rem" -> Execution { p: Player ->
                    val papi = value?.papi(p) ?: throw ParseException("value不可为空")
                    val v = try {
                        papi.toLong()
                    } catch (e: Exception) {
                        throw ExecutionException("${papi}不是有效数值")
                    }
                    CustomCounter[name, p] %= v
                }

                "set" -> Execution { p: Player ->
                    val papi = value?.papi(p) ?: throw ParseException("value不可为空")
                    val v = try {
                        papi.toLong()
                    } catch (e: Exception) {
                        throw ExecutionException("${papi}不是有效数值")
                    }
                    CustomCounter[name, p] = v
                }

                "reset", "remove", "del", "delete" -> Execution { p: Player ->
                    CustomCounter[name, p] = 0
                }

                else -> throw ParseException("未知的处理类型: $type")
            }
        }
        throw ParseException("脚本格式错误: $content")
    }
}