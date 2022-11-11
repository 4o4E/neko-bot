package top.e404.bot.storage

import net.mamoe.mirai.utils.currentTimeMillis
import top.e404.bot.util.getInstance
import top.e404.bot.util.log
import top.e404.bot.util.pmap

object LoadManager {
    private val log = log()
    val list = ArrayList<Loadable>()

    suspend fun onReload(): String {
        val l = currentTimeMillis()
        val result = reload()
            .filter { it.load }
            .groupBy { it.exception == null }
        val success = result[true]?.size ?: 0
        val fail = result[false]?.size ?: 0
        val f = if (fail != 0) "\n失败${fail}个" else ""
        return """重载完成, 耗时${currentTimeMillis() - l}ms
            |成功${success}个$f
        """.trimMargin()
    }

    operator fun get(name: String) = list.firstOrNull { it.matches(name) }
    suspend fun load() = list.pmap { it.onLoad() }
    suspend fun reload() = list.pmap { it.onReload() }
    suspend fun reload(target: List<String>): String {
        val l = currentTimeMillis()
        val map = target.filter { it.trim() != "" }.pmap {
            it to LoadManager[it]?.onReload()
        }.toMap()
        val result = map.entries.groupBy {
            it.value == null
        }
        val invalid = result[true] ?: emptyList()
        val valid = result[false] ?: emptyList()
        val validResult = valid.map { it.value!! }
            .groupBy { it.exception == null }
        val success = validResult[true] ?: emptyList()
        val fail = validResult[false] ?: emptyList()
        val i = if (invalid.isEmpty()) "" else "无效的重载项${invalid.joinToString(", ") { it.key }}\n"
        val s = if (success.isEmpty()) "" else "\n成功${success.size}个"
        val f = if (fail.isEmpty()) "" else "\n失败${fail.size}个"
        return "${i}重载完成, 耗时${currentTimeMillis() - l}ms$s$f"
    }

    suspend fun stop() = list.pmap { it.onStop() }


    suspend fun saveAll() =
        list.filterIsInstance<Savable>().pmap { it.saveImmediately() }

    suspend fun registerAll(classes: List<Class<*>>) {
        val loadableClass = Loadable::class.java
        classes.pmap {
            if (!loadableClass.isAssignableFrom(it)) return@pmap
            try {
                list.add(it.getInstance())
                log.debug("注册loadable`${it.name}`")
            } catch (e: Exception) {
                log.warn("加载Reloadable`${it.name}`时出现异常", e)
            }
        }
        log.debug("共加载${list.size}个自动重载项")
        log.debug("开始处理onLoad")
        list.groupBy {
            it.priority().value
        }.entries.sortedByDescending {
            it.key
        }.forEach {
            it.value.pmap { it.onLoad() }
        }
        log.debug("完成处理onLoad")
    }
}