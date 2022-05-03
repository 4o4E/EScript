package top.e404.escript.util

import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

/**
 * 获得Lore
 *
 * @return Lore, 若为空则返回空列表
 */
fun ItemStack.getLore() =
    itemMeta?.lore ?: emptyList()

/**
 * 设置lore
 *
 * @param lore Lore
 */
fun ItemStack.setLore(lore: List<String>) =
    editItemMeta { this.lore = lore }

/**
 * 获得名字
 *
 * @return 若未设置名字则返回null
 */
fun ItemStack.getName() =
    itemMeta?.displayName

/**
 * 设置名字
 *
 * @param name 名字
 */
fun ItemStack.setName(name: String) =
    editItemMeta { this.setDisplayName(name) }

fun ItemStack.editItemMeta(block: ItemMeta.() -> Unit) = apply {
    val im = itemMeta ?: ItemStack(type).itemMeta!!
    im.block()
    itemMeta = im
}