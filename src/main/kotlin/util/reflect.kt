package top.e404.escript.util

import top.e404.escript.EScript

fun readPluginResource(path: String) =
    EScript.instance
        .getResource(path)!!
        .use { String(it.readBytes()) }

@Suppress("UNCHECKED_CAST")
fun <T> Class<*>.getInstance() = getDeclaredField("INSTANCE").get(this) as? T