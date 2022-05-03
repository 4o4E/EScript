package top.e404.escript.util

import top.e404.escript.script.ParseException

fun String.asDoubleRange(): ClosedFloatingPointRange<Double> {
    try {
        val split = split("..")
        return if (split.size == 1) split[0].toDouble().let { it..it }
        else minOf(
            split[0].toDouble(),
            split[1].toDouble()
        )..maxOf(
            split[0].toDouble(),
            split[1].toDouble()
        )
    } catch (t: Throwable) {
        throw ParseException("${this}不是有效范围", t)
    }
}

fun String.asIntRange(): IntRange {
    try {
        val split = split("..")
        return if (split.size == 1) split[0].toInt().let { it..it }
        else minOf(
            split[0].toInt(),
            split[1].toInt()
        )..maxOf(
            split[0].toInt(),
            split[1].toInt()
        )
    } catch (t: Throwable) {
        throw ParseException("${this}不是有效范围", t)
    }
}