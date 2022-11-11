package top.e404.bot.permission

import kotlinx.serialization.Serializable

/**
 * 代表一个权限持有者, 包括用户权限, 群权限, 权限组
 *
 * @property name 持有者名字, 用户权限前缀`_u`, 群权限前缀`_g`, 权限组名字中不可有_
 * @property desc 简介
 * @property parents 继承的权限组
 * @property perms 包含的权限节点
 */
@Serializable
data class PermContainer(
    val name: String,
    val desc: String = "",
    val parents: List<String> = emptyList(),
    val perms: MutableMap<String, Boolean> = mutableMapOf(),
) {
    fun getPerm(name: String): Boolean? {
        perms[name]?.let { return it }
        for (parent in parents) PermManager.containers[parent]?.getPerm(name)?.let { return it }
        return null
    }
}