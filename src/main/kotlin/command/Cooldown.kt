package top.e404.escript.command

import org.bukkit.command.CommandSender
import top.e404.escript.config.CustomCooldown
import top.e404.escript.config.CustomCooldown.formatMillisToDate
import top.e404.escript.config.CustomCooldown.formatMillisToDuration
import top.e404.escript.config.CustomCooldown.parseDurationToSecond
import top.e404.escript.config.PlayerCache
import top.e404.escript.util.addOnline
import top.e404.escript.util.color
import top.e404.escript.util.sendMsgWithPrefix

object Cooldown : AbstractCommand(
    "cd",
    false,
    "escript.admin"
) {
    override val help = """&a/escript cd info <cd名字> <玩家名字> &f查看玩家${name}的自定义cd信息
        |&a/escript cd set <cd名字> <玩家名字> <时长> &f设置玩家${name}的自定义cd
        |&a/escript cd add <cd名字> <玩家名字> <时长> &f为玩家${name}增加自定义cd时长
        |&a/escript cd minus <cd名字> <玩家名字> <时长> &f为玩家${name}减少自定义cd时长
        |&a/escript cd reset <cd名字> <玩家名字> &f为玩家${name}增加自定义cd时长
    """.trimMargin().color()

    private val l2 = listOf("info", "set", "add", "minus", "reset")

    override fun onTabComplete(
        sender: CommandSender,
        args: Array<out String>,
        complete: MutableList<String>
    ) {
        when (args.size) {
            2 -> complete.addAll(l2)
            3 -> complete.addAll(CustomCooldown.cooldown.keys)
            4 -> complete.addOnline()
        }
    }

    override fun onCommand(sender: CommandSender, args: Array<out String>) {
        if (args.size < 4) {
            sender.sendMessage(help)
            return
        }
        val cd = args[2]
        val playerCd = CustomCooldown.cooldown.getOrPut(cd) { mutableMapOf() }
        val name = args[3]
        val id = PlayerCache.getUuidByName(name)
        if (id == null) {
            sender.sendMsgWithPrefix("&c不存在玩家${name}数据")
            return
        }
        when (args[1].lowercase()) {
            "info" -> {
                val l = playerCd[id]
                if (l == null) {
                    sender.sendMsgWithPrefix("&c玩家${name}当前不在自定义cd($cd)中")
                    return
                }
                sender.sendMsgWithPrefix("&a玩家${name}的自定义cd($cd)将持续到${l.formatMillisToDate()}")
            }
            "set" -> {
                if (args.size != 5) {
                    sender.sendMessage(help)
                    return
                }
                val value = args[4].parseDurationToSecond()
                if (value == null) {
                    sender.sendMsgWithPrefix("&c${args[4]}不是有效时长")
                    return
                }
                playerCd[id] = System.currentTimeMillis() + value * 1000
                sender.sendMsgWithPrefix("&a已设置玩家${name}的自定义cd($cd)时长为${args[4]}")
            }
            "add" -> {
                if (args.size != 5) {
                    sender.sendMessage(help)
                    return
                }
                val value = args[4].parseDurationToSecond()
                if (value == null) {
                    sender.sendMsgWithPrefix("&c${args[4]}不是有效时长")
                    return
                }
                val ocd = playerCd[id] ?: System.currentTimeMillis()
                val v = ocd + value * 1000
                playerCd[id] = v
                sender.sendMsgWithPrefix("&a玩家${name}的自定义cd($cd)将持续到${v.formatMillisToDate()}")
            }
            "minus" -> {
                if (args.size != 5) {
                    sender.sendMessage(help)
                    return
                }
                val value = args[4].parseDurationToSecond()
                if (value == null) {
                    sender.sendMsgWithPrefix("&c${args[4]}不是有效时长")
                    return
                }
                val now = System.currentTimeMillis()
                val ocd = playerCd[id]
                if (ocd == null) {
                    sender.sendMsgWithPrefix("&c玩家${name}当前没有自定义cd($cd)")
                    return
                }
                val ncd = ocd - value * 1000
                val remain = ncd - now
                if (remain <= 0) {
                    playerCd.remove(id)
                    sender.sendMsgWithPrefix("&c已为玩家${name}移除自定义cd")
                    return
                }
                playerCd[id] = ncd
                sender.sendMsgWithPrefix("&a玩家${name}的自定义cd($cd)将持续到${remain.formatMillisToDuration()}")
            }
            "reset" -> {
                sender.sendMsgWithPrefix(
                    if (playerCd.remove(id) != null) "&a已为玩家${name}移除自定义cd($cd)"
                    else "&c玩家${name}当前没有自定义cd($cd)"
                )
            }
            else -> sender.sendMessage(help)
        }
    }
}