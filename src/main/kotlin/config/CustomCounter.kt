package top.e404.escript.config

import org.bukkit.OfflinePlayer
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.scheduler.BukkitTask
import top.e404.escript.config.CustomCooldown.cleanTimeOut
import top.e404.escript.config.CustomCooldown.uuid
import top.e404.escript.util.runTaskLater

object CustomCounter : AbstractConfig("counter.yml", clearBeforeSave = true) {
    // map<计数项目, map<玩家UID, 次数>>
    val count = mutableMapOf<String, MutableMap<String, Long>>()

    private var task: BukkitTask? = null
    private fun scheduleSave() {
        if (task != null) return
        task = runTaskLater(1200) { save(null) }
    }

    override fun YamlConfiguration.beforeSave() = cleanTimeOut()
    override fun YamlConfiguration.onLoad() {
        task?.cancel()
        for (key in getKeys(false)) getConfigurationSection(key)?.let { cdObj ->
            val map = count.getOrPut(key) { mutableMapOf() }
            for (uid in cdObj.getKeys(false)) map[uid] = cdObj.getLong(uid)
        }
    }

    operator fun get(name: String, p: OfflinePlayer) = count.getOrPut(name) { mutableMapOf() }.getOrPut(p.uuid()) { 0 }
    operator fun set(name: String, p: OfflinePlayer, value: Long) {
        count.getOrPut(name) { mutableMapOf() }[p.uuid()] = value
        scheduleSave()
    }
}

