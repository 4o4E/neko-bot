package top.e404.bot.service

import top.e404.bot.mapper.SigninMapper
import top.e404.bot.pojo.SigninData
import top.e404.bot.session
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ConcurrentHashMap

object SigninService {
    private val mapper = session.getMapper(SigninMapper::class.java)!!
    private val sdf = SimpleDateFormat("yyyy.MM.dd")
    fun initTable() = mapper.initTable()

    private val syncMap = ConcurrentHashMap<Long, Any>()

    /**
     * 签到
     *
     * @param id 签到用户的id
     * @return Pair(签到的结果, 上一次签到的数据(可空), 签到完成后的数据)
     */
    fun signin(id: Long): Triple<SigninState, SigninData?, SigninData> {
        synchronized(syncMap.getOrPut(id) { 0 }) {
            val old = mapper.get(id)
            val date = sdf.format(Date())
            val new = SigninData(id, 1, 1, date)
            // 首次签到
            if (old == null) {
                mapper.set(id, 1, 1, date)
                syncMap.remove(id)
                return Triple(SigninState.FIRST, null, new)
            }
            // 重复签到
            if (date == old.date) return Triple(SigninState.REPEAT, old, new)
            // 昨天日期
            val calendar = GregorianCalendar().apply {
                time = Date()
                add(Calendar.DATE, -1)
            }
            // 连续签到
            if (date == sdf.format(calendar.time)) {
                mapper.set(id, old.all + 1, old.con + 1, date)
                syncMap.remove(id)
                return Triple(SigninState.CONTINUOUS, old, new)
            }
            // 中断签到
            mapper.set(id, old.all + 1, 1, date)
            syncMap.remove(id)
            return Triple(SigninState.DISCONTINUOUS, old, new)
        }
    }

    operator fun get(id: Long) = mapper.get(id)

    fun getTop(limit: Int) = mapper.getTop(limit)

    fun set(id: Long, all: Int, con: Int, date: String) = mapper.set(id, all, con, date)
}

enum class SigninState {
    FIRST, // 首次签到
    CONTINUOUS, // 连续签到
    DISCONTINUOUS, // 中断的签到
    REPEAT, // 重复的签到
}