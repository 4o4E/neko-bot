package top.e404.bot.util.collections

/**
 * 代表一个限制大小的列表
 *
 * @property limit 列表的最大上限
 * @property first 若设置为true则列表到达上限时移除第一个, 否则移除最后一个
 */
class LimitList<T>(
    val limit: Int,
    val first: Boolean = true
) : ArrayList<T>() {
    private val lock = ""

    fun remove() = if (first) removeFirst() else removeLast()

    override fun add(element: T): Boolean {
        synchronized(lock) {
            super.add(element)
            if (size > limit) remove()
        }
        return true
    }

    override fun add(index: Int, element: T) {
        synchronized(lock) {
            super.add(index, element)
            if (size > limit) remove()
        }
    }

    override fun addAll(elements: Collection<T>): Boolean {
        synchronized(lock) {
            super.addAll(elements)
            val times = size - limit
            if (times > 0) repeat(times) { remove() }
        }
        return true
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        synchronized(lock) {
            super.addAll(index, elements)
            val times = size - limit
            if (times > 0) repeat(times) { remove() }
        }
        return true
    }
}