package top.e404.bot.mapper

import org.apache.ibatis.annotations.Param
import top.e404.bot.pojo.UsageData

interface UsageMapper {
    /**
     * 初始化表
     */
    fun initTable(): Int

    /**
     * 有则加一, 无则插入一
     */
    fun add(
        @Param("id") id: Long,
        @Param("name") name: String,
        @Param("is_group") isGroup: Boolean,
    )

    /**
     * 获得指令使用top10
     */
    fun get(
        @Param("id") id: Long,
        @Param("is_group") isGroup: Boolean,
    ): ArrayList<UsageData>
}