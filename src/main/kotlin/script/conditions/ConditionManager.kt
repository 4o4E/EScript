package top.e404.escript.script.conditions

import top.e404.escript.script.NoSuchParserException
import top.e404.escript.util.debug
import top.e404.escript.util.getInstance
import top.e404.escript.util.readPluginResource
import top.e404.escript.util.warn

/**
 * 条件编译器管理
 */
object ConditionManager {
    private val parsers = mutableSetOf<ConditionParser>()

    fun load() {
        debug("开始加载条件编译器")
        readPluginResource("condition")
            .split("\n")
            .forEach {
                try {
                    val instance = Class.forName(it).getInstance<ConditionParser>()
                    if (instance !is ConditionParser) {
                        warn("跳过错误的条件编译器$it")
                        return@forEach
                    }
                    registerParser(instance)
                } catch (e: Exception) {
                    warn("注册${it}失败", e)
                }
            }
        debug("完成加载条件编译器")
    }

    fun registerParser(parser: ConditionParser): Boolean {
        return parsers.add(parser).also {
            debug("注册条件编译器${parser.javaClass.name}, ${if (it) "成功" else "已存在"}")
        }
    }

    fun parse(head: String, content: Any?): Condition {
        for (parser in parsers) {
            if (!parser.matchHead(head)) continue
            return parser.parse(content)
        }
        throw NoSuchParserException("不存在解析${head}的条件编译器, 条件: $content")
    }

    fun parse(section: List<*>?) = section?.mapNotNull {
        when (it) {
            null -> return@mapNotNull null
            is Map<*, *> -> {
                val e = it.entries.first()
                parse(e.key.toString(), e.value)
            }

            else -> parse(it.toString(), null)
        }
    } ?: emptyList()
}

