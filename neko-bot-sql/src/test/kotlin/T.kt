package top.e404.bot.sql.test

import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import top.e404.bot.service.HistoryService
import top.e404.bot.service.SigninService
import top.e404.bot.service.UsageService
import top.e404.bot.service.WordService

class T {
    private val log = LoggerFactory.getLogger(T::class.java)

    @Test
    fun initTables() {
        HistoryService.initTable()
        SigninService.initTable()
        UsageService.initTable()
        WordService.initTable()
    }

    @Test
    fun testHistory() {
        HistoryService.initTable()
        repeat(10) {
            HistoryService.add(869951226, 1178264292, false, "ids$it", System.currentTimeMillis().toInt() / 1000, "{}")
        }
        log.info(HistoryService[1178264292, false, "ids1"].toString())
        log.info(HistoryService.getLast(869951226, 1178264292, true, 10).toString())
        HistoryService.delTimeout(System.currentTimeMillis().toInt() / 1000)
        log.info(HistoryService.getLast(869951226, 1178264292, true, 10).toString())
    }

    @Test
    fun testSignin() {
        SigninService.initTable()
        log.info(SigninService.signin(869951226).toString())
        log.info(SigninService.signin(123).toString())
        log.info(SigninService.signin(456).toString())
        log.info(SigninService[869951226].toString())
        log.info(SigninService.getTop(10).toString())
    }

    @Test
    fun testUsage() {
        UsageService.initTable()
        log.info(UsageService.add(869951226, "CommandName1", true).toString())
        log.info(UsageService.add(869951226, "CommandName1", true).toString())
        log.info(UsageService.add(869951226, "CommandName2", true).toString())
        println(UsageService[869951226, true])
    }

    @Test
    fun testWord() {
        WordService.initTable()
        WordService.add(869951226, "你", true)
        WordService.add(869951226, "你", true)
        WordService.add(869951226, "我", true)
        log.info(WordService[869951226, true, 10].toString())
        WordService.delUseless()
    }
}