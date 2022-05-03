package top.e404.escript.script.conditions

import top.e404.escript.script.NoSuchParserException
import top.e404.escript.util.debug
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
                val c = Class.forName(it)
                val instance = c.kotlin.objectInstance
                if (instance !is ConditionParser) {
                    warn("跳过错误的条件编译器$it")
                    return@forEach
                }
                registerParser(instance)
            }
        debug("完成加载条件编译器")
    }

    fun registerParser(parser: ConditionParser): Boolean {
        return parsers.add(parser).also {
            debug("注册条件编译器${parser.javaClass.name}, ${if (it) "成功" else "已存在"}")
        }
    }

    fun parse(pair: Pair<*, *>): Condition {
        val head = pair.first.toString()
        val content = pair.second
        for (parser in parsers) {
            if (!parser.matchHead(head)) continue
            return parser.parse(content)
        }
        throw NoSuchParserException("不存在解析${head}的条件编译器, 条件: $content")
    }
}

