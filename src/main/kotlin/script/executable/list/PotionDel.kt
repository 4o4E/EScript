package top.e404.escript.script.executable.list

import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffectType
import top.e404.escript.annotation.ExecutionSign
import top.e404.escript.script.ParseException
import top.e404.escript.script.executable.Execution
import top.e404.escript.script.executable.ExecutionParser
import top.e404.escript.util.format

@ExecutionSign
object PotionDel : ExecutionParser {
    override val headRegex = Regex("(?i)(del|rm|remove)(effect|potion)")

    override fun parse(content: Any?): Execution {
        if (content == null) throw ParseException("delPotion的内容不可为空")
        val type = content.toString()
        val effect = PotionEffectType.getByName(type.format()) ?: throw ParseException("${type}不是有效药水效果")
        return Execution { p: Player -> p.removePotionEffect(effect) }
    }
}