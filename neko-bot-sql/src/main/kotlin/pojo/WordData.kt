package top.e404.bot.pojo

/**
 * 代表一条分词数据
 *
 * @property word 词
 * @property count 次数
 */
data class WordData(
    val word: String,
    val count: Int,
)
