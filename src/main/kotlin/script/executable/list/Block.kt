package top.e404.escript.script.executable.list

import org.bukkit.Bukkit
import org.bukkit.Material
import top.e404.escript.annotation.ExecutionSign
import top.e404.escript.script.ExecutionException
import top.e404.escript.script.ParseException
import top.e404.escript.script.executable.Execution
import top.e404.escript.script.executable.ExecutionParser
import top.e404.escript.util.format

@ExecutionSign
object Block : ExecutionParser {
    override val headRegex = Regex("(?i)(set)?block")

    override fun parse(content: Any?): Execution {
        if (content !is Map<*, *>) throw ParseException("block格式错误, 脚本: `$content`")
        val map = content.entries.associate { (k, v) -> k.toString().lowercase() to v }
        val world = map["world"]?.toString() ?: throw ParseException("缺少必要参数world, 脚本: `$content`")
        val x = map["x"]?.toString()?.toInt() ?: throw ParseException("缺少必要参数x, 脚本: `$content`")
        val y = map["y"]?.toString()?.toInt() ?: throw ParseException("缺少必要参数y, 脚本: `$content`")
        val z = map["z"]?.toString()?.toInt() ?: throw ParseException("缺少必要参数z, 脚本: `$content`")
        val type = map["type"]?.let {
            try {
                Material.valueOf(it.toString().format())
            } catch (t: Throwable) {
                throw ParseException("错误的Material, 脚本: `$content`", t)
            }
        } ?: throw ParseException("缺少必要参数type, 脚本: `$content`")
        return Execution {
            try {
                val w = Bukkit.getWorld(world) ?: throw ExecutionException("不存在世界`$world`, 脚本: `$content`")
                w.getBlockAt(x, y, z).type = type

            } catch (t: Throwable) {
                throw ExecutionException("设置方块时出现异常, 脚本: `$content`", t)
            }
        }
    }
}