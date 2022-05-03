package top.e404.escript.command

import top.e404.escript.EScript

object CommandManager : AbstractCommandManager(
    EScript.instance,
    listOf(
        Reload,
        Execute,
        Cooldown
    )
)