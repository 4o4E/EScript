package top.e404.escript.script.conditions.list

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import top.e404.escript.annotation.ConditionSign
import top.e404.escript.script.ParseException
import top.e404.escript.script.conditions.Condition
import top.e404.escript.script.conditions.ConditionParser
import top.e404.escript.util.*

@ConditionSign
object ItemHas : ConditionParser {
    override val headRegex = Regex("(?i)hasItem")
    override fun parse(content: Any?): Condition {
        if (content == null) throw ParseException("世界检查的世界不可为空")
        if (content is Map<*, *>) {
            val map = content.format()
            val type = map["type"]?.let {
                try {
                    Material.valueOf(it.toString().format())
                } catch (t: Throwable) {
                    throw ParseException("${it}不是有效物品类型")
                }
            }
            val name = map["name"]?.toString()
            val amount = map["amount"]?.toString()?.toInt() ?: 1
            val lore = map["lore"].asStringList()
            val enchant = try {
                map["enchant"]?.asMap()?.entries?.associate { (k, v) ->
                    val e = k.replace(" ", "_").lowercase()
                    val ench = Enchantment.getByKey(
                        NamespacedKey.minecraft(e)
                    ) ?: throw ParseException("无效附魔: $e")
                    ench to v?.toString()?.toInt()
                } ?: emptyMap()
            } catch (t: Throwable) {
                throw ParseException("错误的附魔: ${map["enchant"]}", t)
            }
            return Condition { p ->
                var a = 0
                a@ for (item in p.inventory) {
                    if (item == null) continue@a
                    // 类型
                    if (type != null && item.type != type) continue@a
                    // 显示名字
                    if (name != null) if (name != item.getName()) continue@a
                    // lore
                    if (lore.isNotEmpty() && item.getLore() != lore) continue@a
                    // 附魔检查
                    val enchantments = item.enchantments
                    if (enchant.size != enchantments.size) continue@a
                    b@ for ((ench, level) in enchant) {
                        if (!enchantments.containsKey(ench)) continue@a
                        val cfgLvl = enchantments[ench] ?: continue@b
                        if (cfgLvl != level) continue@a
                    }
                    a += item.amount
                }
                a >= amount
            }
        }
        val type = try {
            Material.valueOf(content.toString().format())
        } catch (t: Throwable) {
            throw ParseException("${content}不是有效的物品类型")
        }
        return Condition { p: Player -> p.inventory.any { it.type == type } }
    }
}