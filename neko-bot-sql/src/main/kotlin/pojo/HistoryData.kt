package top.e404.bot.pojo

/**
 * 抽象一条聊天记录的
 *
 * @property sender 发送者id
 * @property subject 会话id
 * @property ids 消息链ids的字符串形式
 * @property stamp 发送时间
 * @property json 消息链序列化后的json
 */
data class HistoryData(
    val sender: Long,
    val subject: Long,
    val isGroup: Boolean,
    val ids: String,
    val stamp: Long,
    val json: String
)
