package top.e404.bot.permission

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class DefaultValue {
    @SerialName("true")
    TRUE,

    @SerialName("admin")
    ADMIN,

    @SerialName("false")
    FALSE
}