package top.e404.escript.script.conditions.list

import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffectType
import top.e404.escript.annotation.ConditionSign
import top.e404.escript.script.ParseException
import top.e404.escript.script.conditions.Condition
import top.e404.escript.script.conditions.ConditionParser
import top.e404.escript.util.format

@ConditionSign
object EffectHas : ConditionParser {
    override val headRegex = Regex("(?i)hasEffect")
    override fun parse(content: Any?): Condition {
        if (content == null) throw ParseException("世界检查的世界不可为空")
        if (content is Map<*, *>) {
            val map = content.format()
            val type = map["type"]?.toString() ?: throw ParseException("药水效果类型不可为空")
            val effect = PotionEffectType.getByName(type.format())
                ?: throw ParseException("${type}不是有效药水效果")
            val level = map["level"]?.toString()?.toIntOrNull()
            return Condition { p ->
                val e = p.getPotionEffect(effect) ?: return@Condition false
                if (level == null) true else e.amplifier == level - 1
            }
        }
        val type = content.toString()
        val effect = PotionEffectType.getByName(type.format())
            ?: throw ParseException("${type}不是有效药水效果")
        return Condition { p: Player -> p.getPotionEffect(effect) != null }
    }
}