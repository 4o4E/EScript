package top.e404.escript.script.executable.list

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import top.e404.escript.annotation.ExecutionSign
import top.e404.escript.hook.PlaceholderAPIHook.papi
import top.e404.escript.script.ExecutionException
import top.e404.escript.script.ParseException
import top.e404.escript.script.executable.Execution
import top.e404.escript.script.executable.ExecutionParser

@ExecutionSign
object Teleport : ExecutionParser {
    override val headRegex = Regex("(?i)tp|teleport")

    override fun parse(content: Any?): Execution {
        if (content == null) throw ParseException("title的内容不可为空")
        if (content !is Map<*, *>) {
            throw ParseException("传送脚本格式错误, 脚本: `$content")
        }
        val map = content.entries.associate { (k, v) -> k.toString().lowercase() to v }
        val world = map["world"]?.toString()
            ?: throw ParseException("世界不可为空, 脚本: `$content`")
        val x = map["x"]?.toString()?.toDoubleOrNull()
            ?: throw ParseException("x坐标必须是有效数字, 脚本: `$content`")
        val y = map["y"]?.toString()?.toDoubleOrNull()
            ?: throw ParseException("y坐标必须是有效数字, 脚本: `$content`")
        val z = map["z"]?.toString()?.toDoubleOrNull()
            ?: throw ParseException("z坐标必须是有效数字, 脚本: `$content`")
        val yaw = map["yaw"]?.toString()?.toFloatOrNull() ?: 0F
        val pitch = map["pitch"]?.toString()?.toFloatOrNull() ?: 0F
        return Execution { p: Player ->
            try {
                p.teleport(Location(Bukkit.getWorld(world.papi(p)), x, y, z, yaw, pitch))
            } catch (t: Throwable) {
                throw ExecutionException("向玩家发送标题时出现异常, 脚本: `$content`", t)
            }
        }
    }
}