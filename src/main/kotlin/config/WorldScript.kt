package top.e404.escript.config

import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.scheduler.BukkitTask
import top.e404.escript.script.ScriptManager
import top.e404.escript.util.debug
import top.e404.escript.util.runTaskTimer
import top.e404.escript.util.warn

object WorldScript : AbstractConfig("world.yml") {
    private val tasks = mutableListOf<BukkitTask>()
    override fun YamlConfiguration.onLoad() {
        while (tasks.isNotEmpty()) tasks.removeAt(0).cancel()
        for (worldName in getKeys(false)) {
            debug("开始加载世界${worldName}的脚本")
            for ((i, map) in getMapList(worldName).withIndex()) {
                val durationObject = map["duration"]
                if (durationObject == null) {
                    warn("世界${worldName}的第${i + 1}个定时任务无效, 原因: 缺少duration, 此任务将不会加载")
                    continue
                }
                val ds = durationObject.toString()
                val duration = ds.toLongOrNull()
                if (duration == null) {
                    warn("世界${worldName}的第${i + 1}个定时任务无效, 原因: 无效duration($ds), 此任务将不会加载")
                    continue
                }
                val scriptName = map["script"]?.toString()
                if (scriptName == null) {
                    warn("世界${worldName}的第${i + 1}个定时任务无效, 原因: 缺少script, 此任务将不会加载")
                    continue
                }
                debug("加载世界${worldName}的第${i + 1}个定时脚本")
                tasks.add(runTaskTimer(duration, duration) {
                    debug("运行世界${worldName}的第${i + 1}个定时脚本")
                    val world = Bukkit.getWorld(worldName)
                    if (world == null) {
                        warn("跳过不存在的世界: $worldName")
                        return@runTaskTimer
                    }
                    for (player in world.players) {
                        debug("给玩家${player.name}执行世界${worldName}的第${i}个定时脚本")
                        val script = ScriptManager[scriptName]
                        if (script == null) {
                            warn("跳过不存在的脚本: $scriptName")
                            return@runTaskTimer
                        }
                        script.invoke(player)
                    }
                })
            }
            debug("完成加载世界${worldName}的脚本")
        }
    }
}