package top.e404.escript.util

fun Any?.asList(): List<Any?> {
    if (this == null) return emptyList()
    if (this is Iterable<*>) return toList()
    return listOf(this)
}

fun Any?.asStringList(): List<String> {
    if (this == null) return emptyList()
    if (this is Iterable<*>) return map { it?.toString() ?: "" }
    return listOf(toString())
}

fun Any?.asMap(): Map<String, Any?> {
    if (this == null) return emptyMap()
    if (this is Map<*, *>) return entries.associate { (k, v) -> k.toString() to v }
    return mapOf(toString() to null)
}

fun Map<*, *>.format() =
    entries.associate { (k, v) ->
        k.toString().lowercase() to v
    }