package top.e404.bot.command.parser

sealed interface CommandElement {
    fun asString(): String
}

/**
 * 代表有实际内容的指令元素
 */
interface CommandContentElement : CommandElement {
    val content: String
}

/**
 * 代表一个指令分隔, 不在""内的空格和换行均视作分隔, 多个分隔符自动合并为一个
 */
data class CommandSpacing(
    override var content: String
) : CommandContentElement {
    fun append(char: Char) {
        content = "$content$char"
    }

    override fun asString() = content
}

/**
 * 代表一项参数`-key=value`
 */
data class CommandProp(
    var key: String,
    var value: String
) : CommandElement {
    override fun asString() = buildString {
        append("-")
        if (key.contains(" ") || key.contains("\n")) append("\"$key\"")
        else append(key)
        append("=")
        if (value.contains(" ") || value.contains("\n")) append("\"$value\"")
        else append(value)
    }
}

/**
 * 代表一个参数
 */
data class CommandArg(
    override var content: String
) : CommandContentElement {
    override fun asString() = content
}
