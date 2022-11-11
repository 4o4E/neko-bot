package top.e404.bot.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun Any.log() = LoggerFactory.getLogger(javaClass)!!
fun <T> T.debug(log: Logger, block: (T) -> String) = apply { log.debug(block(this)) }