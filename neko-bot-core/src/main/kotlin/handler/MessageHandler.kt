package top.e404.bot.handler

import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.message.data.MessageChain.Companion.serializeToJsonString
import net.mamoe.mirai.message.data.ids
import net.mamoe.mirai.message.data.time
import top.e404.bot.command.parser.CommandParser
import top.e404.bot.command.parser.ParsedCommand
import top.e404.bot.service.HistoryService
import top.e404.bot.util.log
import java.text.SimpleDateFormat

object MessageHandler {
    private val log = log()
    private val sdf = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")

    fun MessageEvent.onMessage() {
        saveMessage()
        val chain = message
        val parsed = CommandParser.parse(this)
        if (parsed != null) {
            onCommand(parsed)
            return
        }
    }

    /**
     * 保存消息记录
     */
    private fun MessageEvent.saveMessage() {
        try {
            HistoryService.add(
                sender.id,
                subject.id,
                this is GroupMessageEvent,
                message.ids.joinToString(","),
                message.time,
                message.serializeToJsonString()
            )
        } catch (t: Throwable) {
            log.warn(
                "保存消息记录时出现异常, " +
                        "sender: ${sender.id}, " +
                        "${if (this is GroupMessageEvent) "group" else "friend"}: ${subject.id}, " +
                        "time: ${sdf.format(message.time * 1000L)}",
                t
            )
        }
    }

    fun onCommand(parsed: ParsedCommand) {

    }
}