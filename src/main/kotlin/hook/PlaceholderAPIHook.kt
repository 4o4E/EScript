package top.e404.escript.hook

import me.clip.placeholderapi.PlaceholderAPI
import me.clip.placeholderapi.PlaceholderAPIPlugin
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import top.e404.escript.EScript
import top.e404.escript.config.CustomCooldown
import top.e404.escript.util.alsoDebug

object PlaceholderAPIHook : Hook, PlaceholderExpansion() {
    private var papi: PlaceholderAPIPlugin? = null

    override fun enable(): Boolean {
        return papi != null
    }

    override fun update(): Boolean {
        papi = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") as? PlaceholderAPIPlugin
        return enable().alsoDebug {
            if (it) {
                if (!isRegistered) register()
                "检测到PlaceholderAPI, 已启用PlaceholderAPI挂钩"
            } else "未检测到PlaceholderAPI, 已禁用PlaceholderAPI挂钩"
        }
    }

    fun String.papi(p: Player) = if (papi != null) PlaceholderAPI.setPlaceholders(p, this) else this

    override fun getIdentifier() = "escript"
    override fun getAuthor() = "404E"
    override fun getVersion() = EScript.instance.description.version
    override fun persist() = true

    override fun onPlaceholderRequest(
        player: Player?,
        params: String
    ): String {
        val i = params.indexOf("_")
        val head: String
        val value: String
        if (i == -1) {
            head = params
            value = ""
        } else {
            head = params.substring(0, i)
            value = params.substring(i + 1)
        }
        return when (head.lowercase()) {
            "mscd" -> { // %script_mscd_<cdName>%
                if (value == "") return params
                if (player == null) return "null"
                CustomCooldown.getPlayerCooldown(player, value)?.toString() ?: "-1"
            }
            "cd" -> { // %script_cd_<cdName>%
                if (value == "") return params
                if (player == null) return "null"
                CustomCooldown.getPlayerCooldown(player, value)?.let { (it / 1000).toString() } ?: "-1"
            }
            "formatcd" -> { // %script_cd_<cdName>%
                if (value == "") return params
                if (player == null) return "null"
                CustomCooldown.getPlayerCooldown(player, value)?.let { (it / 1000).toString() } ?: "-1"
            }
            "cdstamp" -> { // %script_cdstamp_<cdName>%
                if (value == "") return params
                if (player == null) return "null"
                CustomCooldown.getPlayerCooldownStamp(player, value)?.toString() ?: "-1"
            }
            "incd" -> { // %script_incd%
                if (player == null) return "false"
                val cd = CustomCooldown.getPlayerCooldown(player, value) ?: return "false"
                return (cd > 0).toString()
            }
            // todo
            else -> params
        }
    }
}