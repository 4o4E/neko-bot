package top.e404.bot.pojo

/**
 * 代表一条指令使用次数记录
 *
 * @property name 指令名字
 * @property count 使用次数
 */
data class UsageData(
    val name: String,
    val count: Int,
)
