package top.e404.bot.command.parser

import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.PlainText
import java.util.regex.Pattern

/**
 * 代表一个指令解析器
 *
 * @property messageChain 指令的消息链
 */
class CommandParser(val messageChain: MessageChain) {
    companion object {
        private val p = Pattern.compile("^(?is)(?<prefix>[!！]\\s*)(?<name>\\S+)(\\s(?<content>.*))?")!!
        private val equals = arrayOf('=', '＝')
        private val minus = arrayOf('-', '—')
        private val trim = arrayOf(' ', '　', '\n')
        private val spaceList = arrayOf(' ', '　', '\n')
        private val numberRegex = Regex("\\d+")

        fun parse(event: MessageEvent): ParsedCommand? {
            val parser = CommandParser(event.message)
            if (!parser.parse()) return null
            return ParsedCommand(
                event,
                event.message,
                parser.commandName,
                parser.args,
                parser.props,
                parser.elements,
                event.message.filterIsInstance<Image>()
            )
        }
    }

    val elements by lazy { ArrayList<CommandElement>() }
    val args by lazy { elements.filterIsInstance<CommandArg>().map { it.content }.toMutableList() }
    val props by lazy { elements.filterIsInstance<CommandProp>().associate { it.key to it.value } }

    /**
     * 去除了props并解析原始字符串和转义的文本内容
     */
    val content by lazy {
        elements.filterIsInstance<CommandContentElement>()
            .joinToString("") { it.content }
            .trim { it in trim }
    }

    /**
     * 全部文本内容
     */
    val rawContent = StringBuilder()

    lateinit var prefix: String
    lateinit var commandName: String // 命令名字

    fun parse(): Boolean {
        // 缓存
        var str = false // 是否在解析""包围的内容
        var type = Type.CONTENT // 当前在解析的内容
        var keyCache = ""
        var valueCache = ""
        var cache = ""
        fun prop(value: String) {
            elements.add(
                if (keyCache matches numberRegex
                    && value == ""
                ) CommandArg("-$keyCache")
                else CommandProp(keyCache, value)
            )
            keyCache = ""
            valueCache = ""
            type = Type.CONTENT
        }

        val chain = messageChain.filterIsInstance<PlainText>()
        if (chain.isEmpty()) return false
        var head = true
        var trans = false
        // 处理指令体
        for (e in chain) {
            var s = e.content
            // 处理头数据(commandName)
            if (head) {
                // 解析指令前缀
                val m = p.matcher(s)
                if (!m.find()) return false
                prefix = m.group("prefix")
                commandName = m.group("name")
                if (commandName == "") return false
                // 去除开头`!`
                s = m.group("content") ?: ""
                head = false
                // 若指令无后续内容则return
                if (s.isEmpty()) continue
            }
            //保存到raw
            rawContent.append(s)

            // 按照当前处理模式处理此字符
            fun Char.cache() = when (type) {
                Type.CONTENT -> cache += this
                Type.PROP_KEY -> keyCache += this
                Type.PROP_VALUE -> valueCache += this
            }

            // 切换处理模式
            fun Char.split() {
                when (type) {
                    Type.CONTENT -> if (cache.isNotEmpty()) {
                        elements.add(CommandArg(cache))
                        cache = ""
                    }

                    Type.PROP_KEY -> prop("")
                    Type.PROP_VALUE -> prop(valueCache)
                }
                val last = elements.lastOrNull()
                if (last is CommandSpacing) last.append(this)
                else elements.add(CommandSpacing(this.toString()))
            }

            // 处理
            for (c in s) when {
                // 优先处理转义
                trans -> {
                    when (c) {
                        'n' -> '\n'.cache()
                        '"' -> c.cache()
                        else -> {
                            '\\'.cache()
                            c.cache()
                        }
                    }
                    trans = false
                }

                // 转义字符串
                c == '\\' -> trans = true

                // 若正在处理""包围的内容
                // 若当前字符为"则结束处理""包围的内容
                // 否则直接加入缓存
                str -> if (c == '"') str = false else c.cache()

                // 全/半角空格 换行
                spaceList.contains(c) -> c.split()

                // 若处于KEY解析中则切换为VALUE解析, 不保留`=`
                // 否则作为普通字符处理
                c in equals -> if (type == Type.PROP_KEY) type = Type.PROP_VALUE else c.cache()

                // 若缓存中没有数据(前一个字符为空格), 则进入参数解析模式, 不保留`-`
                // 否则作为普通字符处理
                c in minus -> if (type == Type.CONTENT && cache == "") type = Type.PROP_KEY else c.cache()

                // 切换原始参数模式, 不保留`"`
                c == '"' -> str = true

                // 其他字符, 加入缓存
                else -> c.cache()
            }
        }
        when (type) {
            Type.CONTENT -> if (cache.isNotEmpty()) {
                elements.add(CommandArg(cache))
                cache = ""
            }

            Type.PROP_VALUE -> prop(valueCache)
            Type.PROP_KEY -> prop("")
        }
        return true
    }

    private enum class Type {
        CONTENT, PROP_KEY, PROP_VALUE
    }
}