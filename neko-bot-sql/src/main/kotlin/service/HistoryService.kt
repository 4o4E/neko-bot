package top.e404.bot.service

import top.e404.bot.mapper.HistoryMapper
import top.e404.bot.session

object HistoryService {
    private val mapper = session.getMapper(HistoryMapper::class.java)!!
    fun initTable() = mapper.initTable()
    fun add(
        sender: Long,
        subject: Long,
        isGroup: Boolean,
        ids: String,
        stamp: Int,
        chain: String,
    ) = mapper.add(sender, subject, isGroup, ids, stamp, chain)

    operator fun get(
        subject: Long,
        isGroup: Boolean,
        ids: String
    ) = mapper.get(subject, isGroup, ids)

    fun getLast(
        sender: Long,
        subject: Long,
        isGroup: Boolean,
        count: Int
    ) = mapper.getLast(sender, subject, isGroup, count)

    fun delTimeout(stamp: Int) = mapper.delTimeout(stamp)
}