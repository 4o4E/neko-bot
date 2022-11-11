package top.e404.bot.mapper

import org.apache.ibatis.annotations.Param
import top.e404.bot.pojo.HistoryData

interface HistoryMapper {
    /**
     * 初始化表
     */
    fun initTable(): Int

    /**
     * 插入聊天记录
     */
    fun add(
        @Param("sender") sender: Long,
        @Param("subject") subject: Long,
        @Param("is_group") isGroup: Boolean,
        @Param("ids") ids: String,
        @Param("stamp") stamp: Int,
        @Param("chain") chain: String,
    ): Int

    /**
     * 通过ids获取消息
     */
    fun get(
        @Param("subject") subject: Long,
        @Param("is_group") isGroup: Boolean,
        @Param("ids") ids: String
    ): HistoryData?

    /**
     * 获得最新的n条消息
     */
    fun getLast(
        @Param("sender") sender: Long,
        @Param("subject") subject: Long,
        @Param("is_group") isGroup: Boolean,
        @Param("count") count: Int
    ): List<HistoryData>

    /**
     * 移除超时的消息记录
     */
    fun delTimeout(
        @Param("stamp") stamp: Int,
    ): Int
}