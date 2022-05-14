package top.e404.escript.script.executable.list

import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Particle.DustOptions
import org.bukkit.entity.Player
import top.e404.escript.annotation.ExecutionSign
import top.e404.escript.script.ParseException
import top.e404.escript.script.executable.Execution
import top.e404.escript.script.executable.ExecutionParser
import top.e404.escript.util.format

@ExecutionSign
object Particle : ExecutionParser {
    override val headRegex = Regex("(?i)particle")

    override fun parse(content: Any?): Execution {
        if (content == null) throw ParseException("particle的内容不可为空")
        if (content is Map<*, *>) {
            val map = content.entries.associate { (k, v) -> k.toString().lowercase() to v }
            val type = map["type"]?.toString()?.format() ?: throw ParseException("粒子类型不可为空")
            val particle = try {
                Particle.valueOf(type)
            } catch (t: Throwable) {
                throw ParseException("${type}不是有效粒子类型")
            }
            val x = map["x"]?.toString()?.toDoubleOrNull()
            val y = map["y"]?.toString()?.toDoubleOrNull()
            val z = map["z"]?.toString()?.toDoubleOrNull()
            val count = map["count"]?.toString()?.toIntOrNull() ?: 1
            val offsetX = map["offset_x"]?.toString()?.toDoubleOrNull() ?: 0.0
            val offsetY = map["offset_y"]?.toString()?.toDoubleOrNull() ?: 0.0
            val offsetZ = map["offset_z"]?.toString()?.toDoubleOrNull() ?: 0.0
            val extra = map["extra"]?.toString()?.toDoubleOrNull() ?: 1.0
            val data = map["data"]?.let { e ->
                val m = (e as Map<*, *>).entries.associate { (k, v) ->
                    k.toString().lowercase() to v.toString()
                }
                when (type) {
                    "REDSTONE" -> {
                        val color = m["color"]
                            ?.removePrefix("#")
                            ?.toIntOrNull(16)
                            ?.let { Color.fromRGB(it) }
                            ?: Color.WHITE
                        val size = m["size"]?.toFloatOrNull() ?: 1F
                        DustOptions(color, size)
                    }
                    "ITEM_CRACK" -> try {
                        Material.valueOf(e.toString().format())
                    } catch (t: Throwable) {
                        throw ParseException("材料格式错误")
                    }
                    "BLOCK_CRACK", "BLOCK_DUST", "FALLING_DUST" -> try {
                        Bukkit.createBlockData(Material.valueOf(e.toString().format()))
                    } catch (t: Throwable) {
                        throw ParseException("材料格式错误")
                    }
                    "VIBRATION" -> e.toString().toInt()
                    "DUST_COLOR_TRANSITION" -> {
                        val from = m["from"]
                            ?.removePrefix("#")
                            ?.toIntOrNull(16)
                            ?.let { Color.fromRGB(it) }
                            ?: Color.WHITE
                        val to = m["to"]
                            ?.removePrefix("#")
                            ?.toIntOrNull(16)
                            ?.let { Color.fromRGB(it) }
                            ?: Color.RED
                        Particle.DustTransition(from, to, m["size"]?.toFloat() ?: 1F)
                    }
                    else -> null
                }
            }
            return Execution { p: Player ->
                val l = p.location
                p.world.spawnParticle(
                    particle,
                    x ?: l.x, y ?: l.y, z ?: l.z,
                    count,
                    offsetX, offsetY, offsetZ,
                    extra, data
                )
            }
        }
        val s = content.toString()
        val type = try {
            Particle.valueOf(s.format())
        } catch (t: Throwable) {
            throw ParseException("${s}不是有效粒子类型")
        }
        return Execution { p: Player ->
            p.spawnParticle(type, p.location, 1)
        }
    }
}