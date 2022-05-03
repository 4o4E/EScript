package top.e404.escript.hook

object HookManager {
    val hooks = mutableListOf(
        VaultHook,
        PlaceholderAPIHook
    )

    fun update() {
        for (hook in hooks) hook.update()
    }
}

