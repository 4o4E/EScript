package top.e404.escript.hook

import me.clip.placeholderapi.PlaceholderAPI
import me.clip.placeholderapi.PlaceholderAPIPlugin
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import top.e404.escript.EScript
import top.e404.escript.config.CustomCooldown.cdDuration
import top.e404.escript.config.CustomCooldown.cdEnd
import top.e404.escript.config.CustomCooldown.formatMillisToDate
import top.e404.escript.config.CustomCooldown.formatMillisToDuration
import top.e404.escript.config.CustomCooldown.inCd
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
        if (player == null) return params
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
            "mscd" -> { // 剩余时长 单位ms %script_mscd_<cd-name>%
                if (value == "") return params
                player.cdDuration(value)?.toString() ?: "-1"
            }
            "cd" -> { // 剩余时长 %script_cd_<cd-name>%
                if (value == "") return params
                player.cdDuration(value)?.let { (it / 1000).toString() } ?: "-1"
            }
            "formatedcd" -> { // 剩余时长 格式化 %script_cd_<cd-name>%
                if (value == "") return params
                player.cdDuration(value)?.formatMillisToDuration() ?: ""
            }
            "cdstamp" -> { // 结束时间戳 %script_cdstamp_<cd-name>%
                if (value == "") return params
                player.cdEnd(value)?.toString() ?: "-1"
            }
            "formatedcdstamp" -> { // 结束时间戳 格式化 %script_cdstamp_<cd-name>%
                if (value == "") return params
                player.cdEnd(value)?.formatMillisToDate() ?: "-1"
            }
            "incd" -> { // 是否在cd内 %script_incd_<cd-name>%
                return player.inCd(value).toString()
            }
            // todo
            else -> params
        }
    }
}