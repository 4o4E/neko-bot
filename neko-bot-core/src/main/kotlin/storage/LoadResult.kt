package top.e404.bot.storage

/**
 * 代表一个加载的结果
 *
 * @property load 是否加载
 * @property exception 加载时出现的异常
 * @property time 加载耗时
 */
data class LoadResult(
    val load: Boolean,
    val exception: Throwable? = null,
    val time: Long = 0L,
)