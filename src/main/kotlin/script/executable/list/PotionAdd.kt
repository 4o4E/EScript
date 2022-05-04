package top.e404.escript.script.executable.list

import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import top.e404.escript.annotation.ExecutionSign
import top.e404.escript.script.ParseException
import top.e404.escript.script.executable.Execution
import top.e404.escript.script.executable.ExecutionParser
import top.e404.escript.util.format

@ExecutionSign
object PotionAdd : ExecutionParser {
    override val headRegex = Regex("(?i)add(effect|potion)")

    override fun parse(content: Any?): Execution {
        if (content == null) throw ParseException("addPotion的内容不可为空")
        if (content is Map<*, *>) {
            val map = content.entries.associate { (k, v) -> k.toString().lowercase() to v }
            val type = map["type"]?.toString() ?: throw ParseException("药水效果类型不可为空")
            val effect = PotionEffectType.getByName(type.format())
                ?: throw ParseException("${type}不是有效药水效果")
            val level = map["level"]?.toString()?.toIntOrNull() ?: 1
            val duration = map["duration"]?.toString()?.toIntOrNull() ?: 20
            return Execution { p: Player ->
                p.addPotionEffect(PotionEffect(effect, duration, level - 1))
            }
        }
        val type = content.toString()
        val effect = PotionEffectType.getByName(type.format()) ?: throw ParseException("${type}不是有效药水效果")
        return Execution { p: Player ->
            p.addPotionEffect(PotionEffect(effect, 20, 0))
        }
    }
}