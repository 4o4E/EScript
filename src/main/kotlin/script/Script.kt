package top.e404.escript.script

import org.bukkit.entity.Player
import top.e404.escript.script.conditions.Condition
import top.e404.escript.script.executable.Execution
import top.e404.escript.script.executable.list.Delay
import top.e404.escript.util.debug
import top.e404.escript.util.runTaskLater

/**
 * 代表一个可执行的脚本
 *
 * @property name 脚本名字
 * @property source 脚本来源
 * @property any 判断模式, 默认为all(需要满足所有条件), 若设置为true则只需要满足任意条件即可
 * @property condition 条件列表
 * @property allow 满足条件时执行的脚本
 * @property deny 不满足条件时执行的脚本
 */
class Script(
    val name: String,
    val source: String,
    var any: Boolean = false,
    var condition: List<Condition> = listOf(),
    var allow: List<Execution> = listOf(),
    var deny: List<Execution> = listOf()
) : (Player) -> Unit {
    fun condition(p: Player): Boolean {
        debug("脚本`$name`开始判断, 模式`${if (any) "ANY" else "ALL"}`")
        val b = if (any) condition.any { it.invoke(p) }
        else condition.all { it.invoke(p) }
        debug("脚本`$name`判断结果: ${if (b) "allow" else "deny"}")
        return b
    }

    private fun execute(
        target: Player,
        cause: String,
        startIndex: Int,
        origin: List<Execution>
    ) {
        debug("开始执行脚本`$name`的`${cause}`脚本列表")
        val scripts = ArrayList(origin)
        var index = startIndex
        while (scripts.isNotEmpty()) {
            val func = scripts.removeFirst()
            debug("执行脚本`$name`的`${cause}`脚本列表的第${index}项")
            println(func::class.java.name)
            if (func is Delay.D) { // 延迟
                debug("计划脚本`$name`的延时脚本(delay: ${func.duration}tick, 第${index}项)")
                runTaskLater(func.duration) { execute(target, cause, index, scripts) }
                index++
                return
            }
            try {
                func.invoke(target)
            } catch (t: Throwable) {
                throw ExecutionException("执行脚本`$name`的`${cause}`脚本列表的第${index}项时出现异常, 脚本执行停止", t)
            }
            debug("执行脚本`$name`的`${cause}`脚本列表的第${index}项完成")
            index++
        }
    }

    fun onAllow(p: Player) {
        execute(p, "allow", 1, allow)
    }

    fun onDeny(p: Player) {
        execute(p, "deny", 1, allow)
    }

    override fun invoke(p: Player) {
        if (condition(p)) onAllow(p) else onDeny(p)
    }
}