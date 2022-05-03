@file:Suppress("UNUSED")

package top.e404.escript.util

import org.bukkit.Bukkit
import top.e404.escript.EScript

/**
 * 在下一个游戏刻执行任务
 *
 * @param task 任务
 * @return BukkitTask
 * @see [org.bukkit.scheduler.BukkitScheduler.runTask]
 */
fun runTask(task: () -> Unit) =
    Bukkit.getScheduler().runTask(EScript.instance, task)

/**
 * 在指定的时长后执行任务
 *
 * @param delay 延迟的时长, 单位刻
 * @param task 任务
 * @return BukkitTask
 * @see [org.bukkit.scheduler.BukkitScheduler.runTaskLater]
 */
fun runTaskLater(delay: Long, task: () -> Unit) =
    Bukkit.getScheduler().runTaskLater(EScript.instance, task, delay)

/**
 * 在指定的时长后以指定的周期循环执行任务
 *
 * @param delay 延迟的时长
 * @param period 循环执行任务的周期时长
 * @param task 任务
 * @return BukkitTask
 * @see [org.bukkit.scheduler.BukkitScheduler.runTaskTimer]
 */
fun runTaskTimer(delay: Long, period: Long, task: () -> Unit) =
    Bukkit.getScheduler().runTaskTimer(EScript.instance, task, delay, period)

// async task
/**
 * 在下一个游戏刻执行异步任务
 *
 * @param task 任务
 * @return BukkitTask
 * @see [org.bukkit.scheduler.BukkitScheduler.runTaskAsynchronously]
 */
fun runTaskAsync(task: () -> Unit) =
    Bukkit.getScheduler().runTaskAsynchronously(EScript.instance, task)

/**
 * 在指定的时长后执行任务
 *
 * @param delay 延迟的时长, 单位刻
 * @param task 任务
 * @return BukkitTask
 * @see [org.bukkit.scheduler.BukkitScheduler.runTaskLaterAsynchronously]
 */
fun runTaskLaterAsync(delay: Long, task: () -> Unit) =
    Bukkit.getScheduler().runTaskLaterAsynchronously(EScript.instance, task, delay)

/**
 * 在指定的时长后以指定的周期循环执行任务
 *
 * @param delay 延迟的时长
 * @param period 循环执行任务的周期时长
 * @param task 任务
 * @return BukkitTask
 * @see [org.bukkit.scheduler.BukkitScheduler.runTaskTimerAsynchronously]
 */
fun runTaskTimerAsync(delay: Long, period: Long, task: () -> Unit) =
    Bukkit.getScheduler().runTaskTimerAsynchronously(EScript.instance, task, delay, period)