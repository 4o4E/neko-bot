package top.e404.bot.mapper

import org.apache.ibatis.annotations.Param
import top.e404.bot.pojo.WordData

interface WordMapper {
    /**
     * 初始化表
     */
    fun initTable(): Int

    /**
     * 插入聊天记录
     */
    fun add(
        @Param("id") id: Long,
        @Param("word") word: String,
        @Param("is_group") isGroup: Boolean,
    ): Int

    /**
     * 获取top n的数据
     */
    fun get(
        @Param("id") id: Long,
        @Param("is_group") isGroup: Boolean,
        @Param("limit") limit: Int,
    ): ArrayList<WordData>

    /**
     * 移除记录次数小于10的分词记录
     */
    fun delUseless()
}