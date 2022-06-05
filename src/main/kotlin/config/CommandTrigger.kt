package top.e404.escript.config

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import top.e404.escript.script.Script
import top.e404.escript.script.conditions.Condition
import top.e404.escript.script.conditions.ConditionManager
import top.e404.escript.script.executable.Execution
import top.e404.escript.script.executable.ExecutionManager
import top.e404.escript.util.debug
import top.e404.escript.util.warn

object CommandTrigger : AbstractConfig("command.yml") {
    private val triggers = mutableListOf<Trigger>()

    override fun YamlConfiguration.onLoad() {
        triggers.clear()
        debug("开始从文件中加载指令触发器")
        for (name in getKeys(false)) {
            debug("开始加载${name}")
            val section = getConfigurationSection(name) ?: continue
            val regexString = section.getString("regex") ?: continue
            val regex = try {
                Regex(regexString)
            } catch (t: Throwable) {
                warn("&c无效正则: $regexString, 此检测将会被跳过", t)
                continue
            }

            val any = section.getBoolean("condition_any")
            val condition = ConditionManager.parse(section.getList("condition"))

            val onAllow = ExecutionManager.parse(section.getList("on_allow"))
            val cancelOnAllow = section.getBoolean("cancel_on_allow")

            val onDeny = ExecutionManager.parse(section.getList("on_deny"))
            val cancelOnDeny = section.getBoolean("cancel_on_deny")

            triggers.add(Trigger(name, regex, any, condition, onAllow, cancelOnAllow, onDeny, cancelOnDeny))
            debug("完成加载${name}")
        }
        debug("完成从文件中加载指令触发器, 共载入脚本${triggers.size}条")
    }

    class Trigger(
        override var name: String,
        val regex: Regex,
        override var any: Boolean,
        override var condition: List<Condition>,
        override var allow: List<Execution>,
        val cancelOnAllow: Boolean,
        override var deny: List<Execution>,
        val cancelOnDeny: Boolean,
        override var source: String = "command.yml"
    ) : Script(name, source) {

        /**
         * 检测指令
         *
         * @param event 指令event
         * @return 若不匹配则返回false, 否则处理并返回true
         */
        fun onCommand(event: PlayerCommandPreprocessEvent): Boolean {
            val p = event.player
            if (!regex.matches(event.message)) return false
            val b = condition(p)
            if (b && cancelOnAllow) event.isCancelled = true
            if (!b && cancelOnDeny) event.isCancelled = true
            if (b) onAllow(p) else onDeny(p)
            return true
        }
    }

    fun onEvent(event: PlayerCommandPreprocessEvent) {
        for (trigger in triggers) if (trigger.onCommand(event)) {
            debug("玩家${event.player.name}执行指令${event.message}, 由${trigger.name}处理${if (event.isCancelled) ", 已取消" else ""}")
            return
        }
    }
}