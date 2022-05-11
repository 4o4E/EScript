package top.e404.escript.command

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import top.e404.escript.util.*

object Debug : AbstractCommand(
    "debug",
    false,
    "escript.admin"
) {
    override val help = """&a/escript debug &f切换自己接受debug消息
        |&a/escript debug <玩家名字> &f切换玩家接受debug消息
    """.trimMargin().color()

    override fun onCommand(
        sender: CommandSender,
        args: Array<out String>
    ) {
        if (args.size == 1) {
            if (sender !is Player) {
                sender.sendNotPlayer()
                return
            }
            val del = sender in debuger
            if (del) debuger.remove(sender)
            else debuger.add(sender)
            sender.sendMsgWithPrefix("&a你将${if (del) "不再" else "开始"}接收debug消息")
        }
        if (args.size == 2) {
            val name = args[1]
            val player = Bukkit.getPlayer(name)
            if (player == null) {
                sender.sendMsgWithPrefix("&c玩家${name}不存在")
                return
            }
            val op = player.isOp
            val del = player in debuger
            if (del) debuger.remove(player)
            else debuger.add(player)
            sender.sendMsgWithPrefix("&a${name}(${if (op)"op" else "非op"})将${if (del) "不再" else "开始"}接收debug消息")
            return
        }
        sender.sendMessage(help)
    }

    override fun onTabComplete(
        sender: CommandSender,
        args: Array<out String>,
        complete: MutableList<String>
    ) {
        if (args.size == 2) complete.addOnlineOps()
    }
}