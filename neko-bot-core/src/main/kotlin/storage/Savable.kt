package top.e404.bot.storage

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.concurrent.timerTask

/**
 * 代表一个自动保存的对象
 *
 * @property duration 自动保存间隔, 单位毫秒
 * @property timer 计时器
 */
abstract class Savable : Loadable {
    private val duration = 1000L * 60 * 10
    private val timer = Timer("Savable")
    private var task: TimerTask? = null

    protected abstract fun save()

    /**
     * 若存咋待保存的任务则立即触发保存, 并取消之前任务
     *
     * @return 若触发保存则返回true
     */
    fun saveImmediately(): Boolean {
        if (task == null) return false
        task?.cancel()
        task = null
        var t = System.currentTimeMillis()
        log.debug("开始保存`${name}`")
        val result = runCatching(::save)
        val e = result.exceptionOrNull()
        t = System.currentTimeMillis() - t
        if (e == null) log.debug("完成保存`${name}`, 耗时${t}ms")
        else log.warn("保存`${name}`时出现异常", e)
        return true
    }

    fun scheduleSave() {
        if (task != null) return
        task = timerTask {
            // 保存并取消任务
            runBlocking {
                var t = System.currentTimeMillis()
                log.debug("开始保存`${name}`")
                val result = runCatching(::save)
                val e = result.exceptionOrNull()
                t = System.currentTimeMillis() - t
                if (e == null) log.debug("完成保存`${name}`, 耗时${t}ms")
                else log.warn("保存`${name}`时出现异常", e)
            }
            task?.cancel()
            task = null
        }
        timer.schedule(task, duration)
    }

    override suspend fun onStop() {
        task?.let {
            it.cancel()
            log.info("保存`${name}`数据")
            withContext(Dispatchers.IO) {
                save()
            }
        }
    }
}