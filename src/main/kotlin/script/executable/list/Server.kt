package top.e404.escript.script.executable.list

import com.google.common.io.ByteStreams
import org.bukkit.entity.Player
import top.e404.escript.EScript
import top.e404.escript.annotation.ExecutionSign
import top.e404.escript.hook.PlaceholderAPIHook.papi
import top.e404.escript.script.ExecutionException
import top.e404.escript.script.ParseException
import top.e404.escript.script.executable.Execution
import top.e404.escript.script.executable.ExecutionParser


@ExecutionSign
object Server : ExecutionParser {
    override val headRegex = Regex("(?i)server")

    override fun parse(content: Any?): Execution {
        if (content == null) throw ParseException("server的内容不可为空")
        val s = content.toString()
        return Execution { p: Player ->
            try {
                val server = s.papi(p)
                val out = ByteStreams.newDataOutput()
                out.writeUTF("Connect")
                out.writeUTF(server)
                p.sendPluginMessage(EScript.instance, "BungeeCord", out.toByteArray())
            } catch (t: Throwable) {
                throw ExecutionException("执行子服转发脚本时出现异常, 脚本: `$s`", t)
            }
        }
    }
}