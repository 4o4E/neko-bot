package top.e404.bot.command.handler

import top.e404.bot.command.parser.ParsedCommand
import top.e404.bot.util.log

/**
 * 指令处理器
 */
abstract class CommandHandler {
    companion object {
        @JvmStatic
        val commandHandlers = mutableSetOf<CommandHandler>()

        @JvmStatic
        fun register(handler: CommandHandler) = commandHandlers.add(handler)

        @JvmStatic
        fun unregister(handler: CommandHandler) = commandHandlers.remove(handler)
    }

    /**
     * 指令处理器对应的log
     */
    val log = log()

    /**
     * 指令名字, 用于统计指令使用次数和在帮助中显示
     */
    abstract val name: String

    /**
     * 指令的tag, 用于在帮助中显示
     */
    abstract val tag: Set<String>

    /**
     * 指令名字匹配正则
     */
    abstract val regex: Regex

    /**
     * 处理指令
     *
     * @param content 指令内容
     */
    abstract fun onCommand(content: ParsedCommand)
}

