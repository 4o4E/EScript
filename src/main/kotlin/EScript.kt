package top.e404.escript

import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import top.e404.escript.command.CommandManager
import top.e404.escript.config.Config
import top.e404.escript.config.CustomCooldown
import top.e404.escript.config.PlayerCache
import top.e404.escript.hook.HookManager
import top.e404.escript.hook.PlaceholderAPIHook
import top.e404.escript.listener.PlayerCacheListener
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
        Config.load(null)
        ConditionManager.load()
        ExecutionManager.load()
        ScriptManager.load(null)
        CustomCooldown.load(null)
        PlayerCache.load(null)
        PlayerCache.onSwap()
        HookManager.update()
        PlayerCacheListener.register()
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