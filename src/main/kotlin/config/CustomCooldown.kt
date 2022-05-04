package top.e404.escript.config

import org.bukkit.OfflinePlayer
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.scheduler.BukkitTask
import top.e404.escript.util.runTaskLater
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object CustomCooldown : AbstractConfig("cooldown.yml", clearBeforeSave = true) {
    // map<cd项目, map<玩家UID, 时长>>
    val cooldown = mutableMapOf<String, MutableMap<String, Long>>()

    private var task: BukkitTask? = null
    private fun scheduleSave() {
        if (task != null) return
        task = runTaskLater(1200) { save(null) }
    }

    override fun YamlConfiguration.beforeSave() = cleanTimeOut()
    override fun YamlConfiguration.onLoad() {
        task?.cancel()
        for (key in getKeys(false)) getConfigurationSection(key)?.let { cdObj ->
            val map = cooldown.getOrPut(key) { mutableMapOf() }
            for (uid in cdObj.getKeys(false)) map[uid] = cdObj.getLong(uid)
        }
    }

    fun now() = System.currentTimeMillis()

    fun OfflinePlayer.uuid() = uniqueId.toString()

    /*
     * API
     */

    /**
     * 检测是否在cd内
     *
     * @param uuid 玩家id
     * @param name cd名字
     * @return 若没有此cd则返回false
     */
    fun inCd(uuid: String, name: String): Boolean {
        val end = cdEnd(uuid, name) ?: return false
        return end > now()
    }

    fun OfflinePlayer.inCd(name: String): Boolean {
        return inCd(uuid(), name)
    }

    /**
     * 获取cd剩余时长
     *
     * @param uuid 玩家id
     * @param name cd名字
     * @return 若没有此cd则返回null
     */
    fun cdDuration(uuid: String, name: String): Long? {
        return cdEnd(uuid, name)?.let { it - now() }
    }

    fun OfflinePlayer.cdDuration(name: String): Long? {
        return cdDuration(uuid(), name)
    }

    /**
     * 获取cd结束时间戳
     *
     * @param uuid 玩家uuid
     * @param name cd名字
     * @return 若不存在则返回null
     */
    fun cdEnd(uuid: String, name: String): Long? {
        val map = cooldown[name] ?: return null
        val now = System.currentTimeMillis()
        val l = map[uuid] ?: return null
        if (l <= now) {
            cleanTimeOut(uuid, name)
            return null
        }
        return l
    }

    fun OfflinePlayer.cdEnd(name: String): Long? {
        return cdEnd(uuid(), name)
    }

    /**
     * 通过玩家UUID为其添加一个自定义cd
     *
     * @param uuid 玩家uuid
     * @param name cd名字
     * @param length 时长, 单位毫秒
     * @return 若已有cd项目且时长比新cd长则返回false
     */
    fun setCd(uuid: String, name: String, length: Long): Boolean {
        val map = cooldown.getOrPut(name) { mutableMapOf() }
        val old = map[uuid]
        val s = System.currentTimeMillis() + length
        if (old != null && old > s) return false
        map[uuid] = System.currentTimeMillis() + length
        scheduleSave()
        return true
    }

    fun OfflinePlayer.setCd(name: String, length: Long): Boolean {
        return setCd(uuid(), name, length)
    }

    /**
     * 通过玩家UUID为其添加cd, 若cd不存在或cd已过时则从当前时间开始计算
     *
     * @param uuid 玩家uuid
     * @param name cd名字
     * @param length 时长, 单位毫秒
     * @return 当前时长
     */
    fun addCd(uuid: String, name: String, length: Long): Long {
        val map = cooldown.getOrPut(name) { mutableMapOf() }
        var cd = map[uuid]
        val now = System.currentTimeMillis()
        if (cd == null || cd < now) cd = now
        cd += length
        map[uuid] = cd
        scheduleSave()
        return cd
    }

    fun OfflinePlayer.addCd(name: String, length: Long): Long {
        return addCd(uuid(), name, length)
    }

    /**
     * 通过玩家UUID重置其cd
     *
     * @param uuid 玩家uuid
     * @param name cd名字
     * @return 若重置则返回true
     */
    fun resetCd(uuid: String, name: String): Long? {
        val map = cooldown[name] ?: return null
        val v = map.remove(uuid)
        if (map.isEmpty()) cooldown.remove(name)
        scheduleSave()
        return v
    }

    fun OfflinePlayer.resetCd(name: String): Long? {
        return resetCd(uuid(), name)
    }

    /*
     * 清理
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

    /*
     * 格式化
     */

    private val sdf = SimpleDateFormat("M月d日 H:m:s")

    fun Long.formatMillisToDate() = sdf.format(Date(this))!!

    fun Long.formatMillisToDuration(): String {
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
    fun String.parseDurationToSecond(): Long? {
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

