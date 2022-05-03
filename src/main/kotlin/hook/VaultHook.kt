package top.e404.escript.hook

import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import top.e404.escript.util.alsoDebug

object VaultHook : Hook {
    private var eco: Economy? = null

    override fun enable(): Boolean {
        return eco != null
    }

    override fun update(): Boolean {
        eco = Bukkit.getServer().servicesManager.getRegistration(Economy::class.java)?.provider
        return enable().alsoDebug {
            if (it) "检测到Vault, 已启用Vault挂钩"
            else "未检测到Vault, 已禁用Vault挂钩"
        }
    }

    fun getOrThrow() = eco ?: throw NoVaultFoundException()

    class NoVaultFoundException : Exception("未找到Vault")
}