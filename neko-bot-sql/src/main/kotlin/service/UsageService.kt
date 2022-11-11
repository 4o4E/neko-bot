package top.e404.bot.service

import top.e404.bot.mapper.UsageMapper
import top.e404.bot.session

object UsageService {
    private val mapper = session.getMapper(UsageMapper::class.java)!!
    fun initTable() = mapper.initTable()
    fun add(
        id: Long,
        name: String,
        isGroup: Boolean,
    ) = mapper.add(id, name, isGroup)

    operator fun get(
        id: Long,
        isGroup: Boolean,
    ) = mapper.get(id, isGroup)
}