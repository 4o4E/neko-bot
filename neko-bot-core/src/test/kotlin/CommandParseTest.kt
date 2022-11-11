package top.e404.command_parser.test

import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.messageChainOf
import org.junit.jupiter.api.Test
import top.e404.bot.command.parser.CommandParser

class CommandParseTest {
    @Test
    fun testParse() {
        val chain = messageChainOf(PlainText("!commandName -key1=value1 arg1 -hint"))
        val parser = CommandParser(chain)
        parser.parse()
        println(parser.commandName)
        println(parser.props)
        println(parser.args)
        println(parser.elements)
    }
}