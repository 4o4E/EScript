package top.e404.escript.script.executable.list

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import top.e404.escript.annotation.ExecutionSign
import top.e404.escript.script.ParseException
import top.e404.escript.script.executable.Execution
import top.e404.escript.script.executable.ExecutionParser
import top.e404.escript.util.asMap
import top.e404.escript.util.asStringList
import top.e404.escript.util.format

@ExecutionSign
object ItemTake : ExecutionParser {
    override val headRegex = Regex("(?i)takeItem")

    override fun parse(content: Any?): Execution {
        if (content == null) throw ParseException("takeItem的内容不可为空")
        if (content is Map<*, *>) {
            val map = content.format()
            val takeNotEnough = map["take_not_enough"]?.toString().toBoolean()
            val type = map["type"]?.let {
                try {
                    Material.valueOf(it.toString().format())
                } catch (t: Throwable) {
                    throw ParseException("${it}不是有效物品类型")
                }
            } ?: throw ParseException("giveItem的物品类型不可为空")
            val name = map["name"]?.toString()
            val amount = map["amount"]?.toString()?.toInt() ?: 1
            val lore = map["lore"]?.asStringList()
            val enchant = map["enchant"]?.asMap()?.entries?.associate { (k, v) ->
                val e = k.replace(" ", "_").lowercase()
                val ench = Enchantment.getByKey(
                    NamespacedKey.minecraft(e)
                ) ?: throw ParseException("无效附魔: $e")
                val lvl = v?.toString()?.toInt() ?: 1
                ench to lvl
            } ?: emptyMap()
            return Execution { p: Player ->
                for (item in p.inventory) {
                    if (item == null || item.type == Material.AIR) continue
                    if (item.type != type) continue
                    val im = item.itemMeta ?: continue
                    if (name != null && im.displayName != name) continue
                    if (lore != null && im.lore != lore) continue
                    if (enchant != im.enchants) continue
                    if (takeNotEnough || item.amount >= amount) {
                        item.amount -= amount
                        return@Execution
                    }
                }
            }
        }
        val type = try {
            Material.valueOf(content.toString().format())
        } catch (t: Throwable) {
            throw ParseException("${content}不是有效的物品类型")
        }
        return Execution { p: Player ->
            for (item in p.inventory) {
                if (item == null || item.type == Material.AIR) continue
                if (item.type != type) continue
                item.amount--
            }
        }
    }
}