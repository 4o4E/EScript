package top.e404.escript.config

import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player

object PlayerCache : AbstractConfig("cache.yml") {
    // map<cd项目, map<玩家UID, 时长>>
    private val uuidCache = mutableMapOf<String, String>()
    override fun YamlConfiguration.onLoad() {
        for (key in getKeys(false)) uuidCache[key] = getString(key)!!
    }

    override fun YamlConfiguration.beforeSave() {
        for ((name, uuid) in uuidCache.entries) set(name, uuid)
    }

    fun update(p: Player) {
        uuidCache[p.name] = p.uniqueId.toString()
    }

    fun onSwap() {
        Bukkit.getOnlinePlayers().forEach { update(it) }
    }

    fun getUuidByName(name: String) = uuidCache[name] ?: Bukkit.getPlayer(name)?.uniqueId?.toString()

    fun getNameById(uuid: String) = uuidCache.entries.firstOrNull { it.value == uuid }?.key
}

