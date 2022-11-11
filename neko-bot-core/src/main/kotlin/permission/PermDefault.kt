package top.e404.bot.permission

data class PermDefault(
    val name: String,
    val default: DefaultValue = DefaultValue.TRUE
) {
    companion object {
        fun fromHandler(name: String, default: DefaultValue): PermDefault {
            return PermDefault("command.use.$name", default)
        }
    }

    init {
        PermManager.default[name] = this
    }
}