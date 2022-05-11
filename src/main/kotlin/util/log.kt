package top.e404.escript.util

import org.bukkit.entity.Player
import top.e404.escript.EScript
import top.e404.escript.config.Config
import java.util.logging.Level
import java.util.logging.Logger

private val logger = EScript.instance.logger
private val debugLogger = Logger.getLogger("EScriptDebug")
val debuger = mutableListOf<Player>()

/**
 * 在日志中打印INFO级别的DEBUG日志
 *
 * @param msg 日志内容
 */
fun debug(msg: String) =
    run {
        debuger.forEach { it.sendMsgWithPrefix("&e$msg") }
        if (Config.debug) debugLogger.info("&b$msg".color())
    }

fun <T> T.alsoDebug(block: (T) -> String) = also { debug(block(this)) }

/**
 * 在日志中打印INFO级别的日志
 *
 * @param msg 日志内容
 */
fun info(msg: String) =
    logger.info(msg.color())

/**
 * 在日志中打印WARN级别的日志
 *
 * @param msg 日志内容
 * @param throwable 异常
 */
fun warn(msg: String, throwable: Throwable? = null) =
    if (throwable == null) logger.warning(msg.color())
    else logger.log(Level.WARNING, msg.color(), throwable)

/**
 * 在日志中打印ERROR级别的日志
 *
 * @param msg 日志内容
 * @param throwable 异常
 */
fun error(msg: String, throwable: Throwable? = null) =
    if (throwable == null) logger.log(Level.SEVERE, msg.color())
    else logger.log(Level.SEVERE, msg.color(), throwable)