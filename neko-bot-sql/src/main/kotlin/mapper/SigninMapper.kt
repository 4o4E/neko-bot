package top.e404.bot.mapper

import org.apache.ibatis.annotations.Param
import top.e404.bot.pojo.SigninData

interface SigninMapper {
    /**
     * 初始化表
     */
    fun initTable(): Int

    /**
     * 获得用户的签到数据
     */
    fun get(
        @Param("id") id: Long,
    ): SigninData?

    /**
     * 获得签到top n
     *
     * @param limit n
     */
    fun getTop(@Param("limit") limit: Int): ArrayList<SigninData>

    /**
     * 设置签到数据
     *
     * @param id 用户id
     * @param all 总计签到次数
     * @param con 连续签到次数
     * @param date 最后一次签到日期(yyyy.MM.dd)
     */
    fun set(
        @Param("id") id: Long,
        @Param("all") all: Int,
        @Param("con") con: Int,
        @Param("date") date: String,
    ): Int
}