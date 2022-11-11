package top.e404.bot.storage

import org.slf4j.Logger

/**
 * 加载项, 重写[onLoad]
 */
interface Loadable {
    val log: Logger
    val name: String
    val nameRegex: Regex
    val allowReload: Boolean
    fun priority() = LoadPriority.LOW
    fun onLoad(): LoadResult = LoadResult(false)
    fun onReload(): LoadResult = LoadResult(false)
    suspend fun onStop() {}
    fun matches(name: String) = nameRegex.matches(name)
    fun load(block: () -> Unit): LoadResult {
        var t = System.currentTimeMillis()
        log.debug("开始加载`${name}`")
        val result = runCatching(block)
        val e = result.exceptionOrNull()
        t = System.currentTimeMillis() - t
        if (e == null) log.debug("完成加载`${name}`, 耗时${t}ms")
        else log.warn("加载`${name}`时出现异常", e)
        return LoadResult(
            load = true,
            exception = e,
            time = System.currentTimeMillis() - t
        )
    }
}