package top.e404.bot.pojo

import org.apache.ibatis.annotations.Param

/**
 * 代表一条指令使用次数记录
 *
 * @property id 用户id
 * @property all 总签到次数
 * @property con 连续签到次数
 * @property date 最后一次签到日期
 */
data class SigninData(
    @Param("id") val id: Long,
    @Param("all") val all: Int,
    @Param("con") val con: Int,
    @Param("date") val date: String,
)
