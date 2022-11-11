package top.e404.bot.util

import kotlin.reflect.jvm.javaConstructor

@Suppress("UNCHECKED_CAST")
fun <T> Class<*>.getInstance(): T {
    val constructor = kotlin.constructors.firstOrNull { it.parameters.isEmpty() }
        ?: return kotlin.objectInstance as T
    return constructor.javaConstructor!!.newInstance() as T
}