package top.e404.escript.script.conditions.list

import org.bukkit.Material
import top.e404.escript.annotation.ConditionSign
import top.e404.escript.script.conditions.Condition
import top.e404.escript.script.conditions.ConditionParser

@ConditionSign
object WaterOut : ConditionParser {
    override val headRegex = Regex("(?i)outWater|notInWater")
    override fun parse(content: Any?) = Condition {
        it.world.getBlockAt(it.location).type == Material.WATER
    }
}