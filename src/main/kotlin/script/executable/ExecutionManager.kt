package top.e404.escript.script.executable

import top.e404.escript.script.NoSuchParserException
import top.e404.escript.script.executions.allExecutions

/**
 * 执行编译器管理
 */
object ExecutionManager {
    fun parse(head: String, content: Any?): Execution {
        for (parser in allExecutions) {
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