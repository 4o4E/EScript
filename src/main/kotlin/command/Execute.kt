package top.e404.escript.command

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import top.e404.escript.script.ScriptManager
import top.e404.escript.util.color
import top.e404.escript.util.sendMsgWithPrefix

object Execute : AbstractCommand(
    "exec",
    false,
    "escript.admin"
) {
    override val help = "&a/escript exec <脚本名> <玩家id> &f以玩家为目标执行脚本".color()
    override fun onCommand(
        sender: CommandSender,
        args: Array<out String>
    ) {
        if (args.size != 3) {
            sender.sendMessage(help)
            return
        }
        val script = ScriptManager[args[1]]
        if (script == null) {
            sender.sendMsgWithPrefix("&c不存在脚本${args[1]}")
            return
        }
        val p = Bukkit.getPlayer(args[2])
        if (p == null) {
            sender.sendMsgWithPrefix("&c不存在玩家${args[2]}")
            return
        }
        script.invoke(p)
    }

    override fun onTabComplete(
        sender: CommandSender,
        args: Array<out String>,
        complete: MutableList<String>
    ) {
        when (args.size) {
            2 -> complete.addAll(ScriptManager.scripts.keys)
            3 -> Bukkit.getOnlinePlayers().forEach { complete.add(it.name) }
        }
    }
}