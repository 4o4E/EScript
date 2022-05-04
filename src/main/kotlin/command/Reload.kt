package top.e404.escript.command

import org.bukkit.command.CommandSender
import top.e404.escript.config.Config
import top.e404.escript.config.CustomCooldown
import top.e404.escript.config.WorldScript
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
        Config.load(sender)
        ScriptManager.load(sender)
        CustomCooldown.load(sender)
        WorldScript.load(sender)
        HookManager.update()
        sender.sendMsgWithPrefix("&a重载完成")
    }
}