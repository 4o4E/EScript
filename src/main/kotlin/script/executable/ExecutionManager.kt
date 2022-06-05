package top.e404.escript.script.executable

import top.e404.escript.script.NoSuchParserException
import top.e404.escript.util.debug
import top.e404.escript.util.readPluginResource
import top.e404.escript.util.warn

/**
 * 执行编译器管理
 */
object ExecutionManager {
    private val parsers = mutableSetOf<ExecutionParser>()

    fun load() {
        debug("开始加载执行编译器")
        readPluginResource("execution")
            .split("\n")
            .forEach {
                val c = Class.forName(it)
                val instance = c.kotlin.objectInstance
                if (instance !is ExecutionParser) {
                    warn("跳过错误的执行编译器$it")
                    return@forEach
                }
                registerParser(instance)
            }
        debug("完成加载执行编译器")
    }

    fun registerParser(parser: ExecutionParser): Boolean {
        return parsers.add(parser).also {
            debug("注册执行编译器${parser.javaClass.name}, ${if (it) "成功" else "已存在"}")
        }
    }

    fun parse(head: String, content: Any?): Execution {
        for (parser in parsers) {
            if (!parser.matchHead(head)) continue
            return parser.parse(content)
        }
        throw NoSuchParserException("不存在解析${head}的执行编译器, 脚本: $content")
    }

    fun parse(list: List<*>?) = list?.mapNotNull {
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