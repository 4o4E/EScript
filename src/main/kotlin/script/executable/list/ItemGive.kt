package top.e404.escript.script.executable.list

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import top.e404.escript.annotation.ExecutionSign
import top.e404.escript.script.ParseException
import top.e404.escript.script.executable.Execution
import top.e404.escript.script.executable.ExecutionParser
import top.e404.escript.util.asMap
import top.e404.escript.util.asStringList
import top.e404.escript.util.editItemMeta
import top.e404.escript.util.format

@ExecutionSign
object ItemGive : ExecutionParser {
    override val headRegex = Regex("(?i)giveItem")

    override fun parse(content: Any?): Execution {
        if (content == null) throw ParseException("giveItem的内容不可为空")
        if (content is Map<*, *>) {
            val map = content.format()
            val dropOnFull = map["drop_on_full"].toString().toBoolean()
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
                val item = ItemStack(type, amount).apply {
                    editItemMeta {
                        name?.let { setDisplayName(it) }
                        lore?.let { setLore(it) }
                    }
                    for ((ench, lvl) in enchant) addUnsafeEnchantment(ench, lvl)
                }
                val empty = p.inventory.firstEmpty()
                if (empty == -1 && dropOnFull) {
                    p.world.dropItem(p.location, item)
                }
                p.inventory.setItem(empty, item)
            }
        }
        val type = try {
            Material.valueOf(content.toString().format())
        } catch (t: Throwable) {
            throw ParseException("${content}不是有效的物品类型")
        }
        val item = ItemStack(type)
        return Execution { p: Player ->
            val empty = p.inventory.firstEmpty()
            if (empty != -1) p.inventory.setItem(empty, item)
        }
    }
}