package top.e404.escript.config

import org.bukkit.configuration.file.YamlConfiguration

object Config : AbstractConfig("config.yml") {
    var prefix = "&7[&6EScript&7]"
    var debug = false
    var update = true
    override fun YamlConfiguration.onLoad() {
        getString("prefix")?.also { prefix = it }
        debug = getBoolean("debug")
        update = getBoolean("update")
    }
}