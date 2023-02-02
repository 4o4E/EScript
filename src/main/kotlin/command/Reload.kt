package top.e404.escript.command

import org.bukkit.command.CommandSender
import top.e404.escript.config.*
import top.e404.escript.hook.HookManager
import top.e404.escript.script.ScriptManager
import top.e404.escript.util.color
import top.e404.escript.util.sendMsgWithPrefix

object Reload : AbstractCommand(
    "reload",
    false,
    "escript.admin"
) {
    override val help = "&a/escript reload &f重载".color()
    override fun onCommand(sender: CommandSender, args: Array<out String>) {
        Config.load(sender) // 配置文件
        ScriptManager.load(sender) // 脚本管理器
        CommandTrigger.load(sender) // 指令触发器
        CustomCooldown.load(sender) // 自定义cd
        CustomCounter.load(sender) // 自定义计数器
        WorldScript.load(sender) // 世界脚本
        HookManager.update() // 更新挂钩
        sender.sendMsgWithPrefix("&a重载完成")
    }
}