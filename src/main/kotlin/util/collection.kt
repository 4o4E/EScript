package top.e404.escript.util

import org.bukkit.Bukkit

fun MutableList<String>.addOnline() = Bukkit.getOnlinePlayers().forEach { add(it.toString()) }