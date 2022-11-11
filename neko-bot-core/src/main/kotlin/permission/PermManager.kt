package top.e404.bot.permission

import net.mamoe.mirai.contact.Group

/**
 * 权限管理
 *
 * 群权限检查 > 用户权限检查 > 权限组检查 > 权限默认值 > false
 */
object PermManager {
    val default = mutableMapOf<String, PermDefault>() // 已注册的权限节点
    var containers = mutableMapOf<String, PermContainer>() // 从配置文件中加载的权限数据

    operator fun get(name: String) = containers[name]

    operator fun get(id: Long, isGroup: Boolean) =
        if (isGroup) get("_g$id")
        else get("_u$id")

    /**
     * 获取群权限容器
     *
     * @param gid 群id
     * @return 群权限容器
     */
    fun getGroupContainer(gid: Long) = get("_g$gid")

    /**
     * 获取用户权限容器
     *
     * @param uid 用户id
     * @return 用户权限容器
     */
    fun getUserContainer(uid: Long) = get("_u$uid")

    fun hasPermission(
        permission: String,
        container: PermContainer
    ): Boolean? {
        container.perms[permission]?.let { return it }
        for (parent in container.parents) {
            val parentContainer = containers[parent] ?: continue
            hasPermission(permission, parentContainer)?.let {
                return it
            }
        }
        return null
    }

    fun Group.hasPerm(perm: String): Boolean {
        getGroupContainer(id)?.let { container ->
            hasPermission(perm, container)?.let { return it }
        }
        return getDefaultPerm(perm) == DefaultValue.TRUE
    }

    /**
     * 获取注册了的权限的默认值
     *
     * @param perm 权限名
     * @return 若未设置则返回null
     */
    fun getDefaultPerm(perm: String) = default[perm]?.default
}