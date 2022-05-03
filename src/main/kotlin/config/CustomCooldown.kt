package top.e404.escript.config

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object CustomCooldown : AbstractConfig("cooldown.yml", clearBeforeSave = true) {
    // map<cd项目, map<玩家UID, 时长>>
    val cooldown = mutableMapOf<String, MutableMap<String, Long>>()
    override fun YamlConfiguration.onLoad() {
        for (key in getKeys(false)) getConfigurationSection(key)?.let { cdObj ->
            val map = cooldown.getOrPut(key) { mutableMapOf() }
            for (uid in cdObj.getKeys(false)) map[uid] = cdObj.getLong(uid)
        }
    }

    /**
     * 获取玩家自定cd的到期时间戳
     *
     * @param p 玩家
     * @param name cd名字
     * @return cd到期时间戳, 若不存在则返回null
     */
    fun getPlayerCooldownStamp(p: Player, name: String): Long? {
        return getPlayerCooldownStampByUuid(p.uniqueId.toString(), name)
    }

    /**
     * 通过玩家UUID获得其自定cd的到期时间戳
     *
     * @param uuid 玩家uuid
     * @param name cd名字
     * @return cd到期时间戳, 若不存在则返回null
     */
    fun getPlayerCooldownStampByUuid(uuid: String, name: String): Long? {
        val map = cooldown[name] ?: return null
        val now = System.currentTimeMillis()
        val l = map[uuid] ?: return null
        if (l < now) {
            cleanTimeOut(uuid, name)
            return null
        }
        return l
    }

    /**
     * 获取玩家自定cd的剩余时长
     *
     * @param p 玩家
     * @param name cd名字
     * @return cd剩余时长, 若不存在则返回null
     */
    fun getPlayerCooldown(p: Player, name: String): Long? {
        return getPlayerCooldownByUuid(p.uniqueId.toString(), name)
    }

    /**
     * 通过玩家UUID获取其自定cd的剩余时长
     *
     * @param uuid 玩家uuid
     * @param name cd名字
     * @return cd剩余时长, 若不存在则返回null
     */
    fun getPlayerCooldownByUuid(uuid: String, name: String): Long? {
        val stamp = getPlayerCooldownStampByUuid(uuid, name) ?: return null
        return stamp - System.currentTimeMillis()
    }

    /**
     * 给玩家添加一个自定义cd
     *
     * @param p 玩家
     * @param name cd名字
     * @param length 时长, 单位毫秒
     * @return 若已有cd项目且时长比新cd长则返回false
     */
    fun setPlayerCooldown(p: Player, name: String, length: Long): Boolean {
        return setPlayerCooldownByUuid(p.uniqueId.toString(), name, length)
    }

    /**
     * 通过玩家UUID为其添加一个自定义cd
     *
     * @param uuid 玩家uuid
     * @param name cd名字
     * @param length 时长, 单位毫秒
     * @return 若已有cd项目且时长比新cd长则返回false
     */
    fun setPlayerCooldownByUuid(uuid: String, name: String, length: Long): Boolean {
        val map = cooldown.getOrPut(name) { mutableMapOf() }
        val old = map[uuid]
        val s = System.currentTimeMillis() + length
        if (old != null && old > s) return false
        map[uuid] = System.currentTimeMillis() + length
        return true
    }

    /**
     * 给玩家添加cd, 若cd不存在或cd已过时则从当前时间开始计算
     *
     * @param p 玩家
     * @param name cd名字
     * @param length 时长, 单位毫秒
     * @return 当前时长
     */
    fun addPlayerCooldown(p: Player, name: String, length: Long): Long {
        return addPlayerCooldownByUuid(p.uniqueId.toString(), name, length)
    }

    /**
     * 通过玩家UUID为其添加cd, 若cd不存在或cd已过时则从当前时间开始计算
     *
     * @param uuid 玩家uuid
     * @param name cd名字
     * @param length 时长, 单位毫秒
     * @return 当前时长
     */
    fun addPlayerCooldownByUuid(uuid: String, name: String, length: Long): Long {
        val map = cooldown.getOrPut(name) { mutableMapOf() }
        var cd = map[uuid]
        val now = System.currentTimeMillis()
        if (cd == null || cd < now) cd = now
        cd += length
        map[uuid] = cd
        return cd
    }

    /**
     * 重置玩家cd
     *
     * @param p 玩家
     * @param name cd名字
     * @return 若重置则返回true
     */
    fun resetPlayerCooldown(p: Player, name: String): Long? {
        return resetPlayerCooldownByUuid(p.uniqueId.toString(), name)
    }

    /**
     * 通过玩家UUID重置其cd
     *
     * @param uuid 玩家uuid
     * @param name cd名字
     * @return 若重置则返回true
     */
    fun resetPlayerCooldownByUuid(uuid: String, name: String): Long? {
        val map = cooldown[name] ?: return null
        val v = map.remove(uuid)
        if (map.isEmpty()) cooldown.remove(name)
        return v
    }

    /**
     * 清理无效的cd
     */
    fun cleanTimeOut() {
        val l = System.currentTimeMillis()
        for (map in cooldown.values) {
            map.entries.removeIf { it.value < l }
        }
        cooldown.entries.removeIf { it.value.isEmpty() }
    }

    private fun cleanTimeOut(uuid: String, name: String) {
        cooldown[name]!!.remove(uuid)
    }

    private val sdf = SimpleDateFormat("M月d日 H:m:s")

    fun Long.formatAsDate() = sdf.format(Date(this))!!

    fun Long.formatAsMillis(): String {
        var t = this / 1000
        var s = ""
        var temp = t % 60
        if (temp != 0L) s = "${temp}秒"
        t /= 60
        if (t == 0L) return s
        temp = t % 60
        if (temp != 0L) s = "${temp}分$s"
        t /= 60
        if (t == 0L) return s
        temp = t % 24
        if (temp != 0L) s = "${temp}时$s"
        t /= 24
        if (t == 0L) return s
        s = "${t}天$s"
        return s
    }

    private val durationPattern =
        Pattern.compile("^((?<d>\\d+)[d天日])?((?<h>\\d+)(h|(小)?时))?((?<m>\\d+)(m(in)?|分(钟)?))?((?<s>\\d+)[s秒]?)?$")

    /**
     * 解析1d1h1m格式的字符串为单位秒的时长
     *
     * @return 时长, 解析异常返回null
     */
    fun String.parseAsSecond(): Long? {
        toLongOrNull()?.let { return it }
        val m = durationPattern.matcher(this).apply {
            if (!find()) return null
        }
        var result = 0L //结果 单位秒
        //天
        result += try {
            m.group("d").toLong() * 86400
        } catch (e: Throwable) {
            0
        }
        //时
        result += try {
            m.group("h").toLong() * 3600
        } catch (e: Throwable) {
            0
        }
        //分
        result += try {
            m.group("m").toLong() * 60
        } catch (e: Throwable) {
            0
        }
        //秒
        result += try {
            m.group("s").toLong()
        } catch (e: Throwable) {
            0
        }
        return result
    }
}

