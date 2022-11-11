package top.e404.bot.service

import top.e404.bot.mapper.WordMapper
import top.e404.bot.session

object WordService {
    private val mapper = session.getMapper(WordMapper::class.java)!!
    fun initTable() = mapper.initTable()
    fun add(
        id: Long,
        word: String,
        isGroup: Boolean,
    ) = mapper.add(id, word, isGroup)

    operator fun get(
        id: Long,
        isGroup: Boolean,
        limit: Int,
    ) = mapper.get(id, isGroup, limit)

    fun delUseless() = mapper.delUseless()
}