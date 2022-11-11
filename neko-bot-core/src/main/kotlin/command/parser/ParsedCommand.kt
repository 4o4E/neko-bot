package top.e404.bot.command.parser

import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.MessageChain

/**
 * 指令解析的结果
 *
 * @property event 指令属于的消息event
 * @property messageChain 原始的指令消息链
 * @property commandName 指令名
 * @property commandArgs 指令参数
 * @property commandProps 指令键值对参数
 * @property commandElements 完整的指令元素列表
 * @property images 指令中的图片
 */
data class ParsedCommand(
    val event: MessageEvent,
    val messageChain: MessageChain,
    val commandName: String,
    val commandArgs: List<String>,
    val commandProps: Map<String, String>,
    val commandElements: List<CommandElement>,
    val images: List<Image>,
)
