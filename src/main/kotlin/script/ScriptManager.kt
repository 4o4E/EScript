package top.e404.escript.script

import org.bukkit.command.CommandSender
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.YamlConfiguration
import top.e404.escript.EScript
import top.e404.escript.script.conditions.ConditionManager
import top.e404.escript.script.executable.ExecutionManager
import top.e404.escript.util.debug
import top.e404.escript.util.sendOrElse
import top.e404.escript.util.warn
import java.io.File

object ScriptManager {
    private val scriptDir = EScript.instance.dataFolder.resolve("scripts")

    val scripts = mutableMapOf<String, Script>()

    operator fun get(name: String) = scripts[name]

    fun load(sender: CommandSender?) {
        scripts.clear()
        if (!scriptDir.exists()) {
            scriptDir.mkdirs()
            EScript.instance.getResource("example.yml")!!.use {
                scriptDir.resolve("example.yml").writeBytes(it.readBytes())
            }
            return
        }
        val files = scriptDir.listFiles() ?: return
        debug("开始从文件中加载脚本")
        for (file in files) file.load(sender)
        debug("完成从文件中加载脚本, 共载入脚本${scripts.size}条")
    }

    private fun File.load(sender: CommandSender?) {
        if (isFile) {
            val path = absolutePath
            debug("开始加载${path}")
            val y = YamlConfiguration()
            try {
                y.load(this)
            } catch (ice: InvalidConfigurationException) {
                val s = "无效的配置文件: ${absolutePath}"
                sender.sendOrElse(s) { warn(s, ice) }
                return
            } catch (t: Throwable) {
                warn("加载配置文件时出现异常", t)
                return
            }
            for (name in y.getKeys(false)) {
                val old = scripts[name]
                if (old != null) {
                    val s = "名为${name}的脚本已存在, 来源: ${old.source}"
                    sender.sendOrElse(s) { warn(s) }
                    continue
                }
                val script = try {
                    y.getScript(name, absolutePath)
                } catch (t: Throwable) {
                    val s = "加载脚本${name}时出现异常, 此脚本将不被加载"
                    sender.sendOrElse(s) { warn(s, t) }
                    continue
                }
                scripts[name] = script
            }
            debug("完成加载${path}")
            return
        }
        listFiles()?.forEach { it.load(sender) }
    }

    fun ConfigurationSection.getScript(path: String, source: String) =
        getConfigurationSection(path)?.let { cfg ->
            debug("开始加载脚本$path")
            val name = path.lastIndexOf('.').let { i ->
                if (i == -1) path else path.substring(i)
            }
            val any = cfg.getBoolean("condition_any")
            debug("开始加载脚本$path(条件)")
            val condition = ConditionManager.parse(cfg.getList("condition"))
            debug("完成加载脚本$path(条件), 共${condition.size}条")
            debug("开始加载脚本$path(on_allow)")
            val onAllow = ExecutionManager.parse(cfg.getList("on_allow"))
            debug("完成加载脚本$path(on_allow), 共${onAllow.size}项")
            debug("开始加载脚本$path(on_deny)")
            val onDeny = ExecutionManager.parse(cfg.getList("on_deny"))
            debug("完成加载脚本$path(on_deny), 共${onDeny.size}项")
            Script(name, source, any, condition, onAllow, onDeny)
        } ?: throw ParseException("无效脚本: $path")
}