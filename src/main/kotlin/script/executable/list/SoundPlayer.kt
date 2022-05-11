package top.e404.escript.script.executable.list

import org.bukkit.Sound
import org.bukkit.entity.Player
import top.e404.escript.annotation.ExecutionSign
import top.e404.escript.script.ParseException
import top.e404.escript.script.executable.Execution
import top.e404.escript.script.executable.ExecutionParser
import top.e404.escript.util.format

@ExecutionSign
object SoundPlayer : ExecutionParser {
    override val headRegex = Regex("(?i)soundP(layer)?")

    override fun parse(content: Any?): Execution {
        if (content == null) throw ParseException("sound的内容不可为空")
        if (content is Map<*, *>) {
            val map = content.entries.associate { (k, v) -> k.toString().lowercase() to v }
            val type = map["type"]?.toString() ?: throw ParseException("声音类型不可为空")
            val sound = try {
                Sound.valueOf(type.format())
            } catch (t: Throwable) {
                throw ParseException("${type}不是有效声音类型")
            }
            val volume = map["volume"]?.toString()?.toFloatOrNull() ?: 1F
            val pitch = map["pitch"]?.toString()?.toFloatOrNull() ?: 1F
            return Execution { p: Player ->
                p.playSound(p.location, sound, volume, pitch)
            }
        }
        val type = content.toString()
        val sound = try {
            Sound.valueOf(type.format())
        } catch (t: Throwable) {
            throw ParseException("${type}不是有效声音类型")
        }
        return Execution { p: Player ->
            p.playSound(p.location, sound, 1F, 1F)
        }
    }
}