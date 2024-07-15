package top.e404.escript.script.conditions

import top.e404.escript.script.NoSuchParserException

/**
 * 条件编译器管理
 */
object ConditionManager {
    fun parse(head: String, content: Any?): Condition {
        for (parser in allConditions) {
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

