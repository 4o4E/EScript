package top.e404.escript.script.executable.list

import org.bukkit.entity.Player
import top.e404.escript.annotation.ExecutionSign
import top.e404.escript.hook.VaultHook
import top.e404.escript.script.ParseException
import top.e404.escript.script.executable.Execution
import top.e404.escript.script.executable.ExecutionParser

@ExecutionSign
object MoneyTake : ExecutionParser {
    override val headRegex = Regex("(?i)take(money|bal(ance)?)")

    override fun parse(content: Any?): Execution {
        if (content == null) throw ParseException("money的内容不可为空")
        val d = content.toString().toDoubleOrNull() ?: throw ParseException("money的内容必须为有效数字")
        return Execution { p: Player ->
            val economy = VaultHook.getOrThrow()
            if (d < 0) economy.depositPlayer(p, d)
            if (d > 0) economy.withdrawPlayer(p, d)
        }
    }
}