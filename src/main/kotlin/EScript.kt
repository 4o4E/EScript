package top.e404.escript

import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import top.e404.escript.command.CommandManager
import top.e404.escript.config.*
import top.e404.escript.hook.HookManager
import top.e404.escript.hook.PlaceholderAPIHook
import top.e404.escript.listener.PlayerCacheListener
import top.e404.escript.listener.PlayerCommandListener
import top.e404.escript.script.ScriptManager
import top.e404.escript.script.conditions.ConditionManager
import top.e404.escript.script.executable.ExecutionManager
import top.e404.escript.update.Update
import top.e404.escript.util.color
import top.e404.escript.util.info

class EScript : JavaPlugin() {
    companion object {
        lateinit var instance: EScript
    }

    override fun onEnable() {
        instance = this
        Metrics(this, 15118)
        Config.load(null) // 配置文件

        ConditionManager.load() // 条件编译器管理
        ExecutionManager.load() // 执行编译器管理

        ScriptManager.load(null) // 脚本管理器
        CommandTrigger.load(null) // 指令触发器
        CustomCooldown.load(null) // 自定义cd
        WorldScript.load(null) // 世界脚本
        HookManager.update() // 更新挂钩

        PlayerCache.load(null) // 玩家数据缓存
        PlayerCache.onSwap()

        PlayerCacheListener.register()
        PlayerCommandListener.register()

        server.messenger.registerOutgoingPluginChannel(this, "BungeeCord")

        CommandManager.register("escript")
        Update.init()
        info("&a加载完成, 作者404E".color())
    }

    override fun onDisable() {
        Bukkit.getScheduler().cancelTasks(this)
        CustomCooldown.save(null)
        PlayerCache.save(null)
        PlaceholderAPIHook.unregister()
        info("&a已卸载, 感谢使用".color())
    }
}