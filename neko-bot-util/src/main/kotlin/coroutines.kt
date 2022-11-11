@file:Suppress("UNUSED")

package top.e404.bot.util

import kotlinx.coroutines.*

suspend fun <A, B> Iterable<A>.pmap(block: suspend (A) -> B) =
    coroutineScope {
        map { async { block(it) } }.awaitAll()
    }.toMutableList()

fun <A, B> Iterable<A>.pmap(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    block: suspend (A) -> B
) = runBlocking(dispatcher) {
    pmap(block)
}

suspend fun <A, B> Iterable<A>.pmapNotNull(block: suspend (A) -> B?): MutableList<B> =
    coroutineScope {
        map { async { block(it) } }.awaitAll()
    }.filterNotNull().toMutableList()

fun <A, B> Iterable<A>.pmapNotNull(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    block: suspend (A) -> B?
) = runBlocking(dispatcher) {
    pmapNotNull(block)
}

suspend fun <A, B> Iterable<A>.pmapIndexed(block: suspend (A, Int) -> B): MutableList<B> =
    coroutineScope {
        mapIndexed { index, a -> async { block(a, index) } }.awaitAll()
    }.toMutableList()

fun <A, B> Iterable<A>.pmapIndexed(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    block: suspend (A, Int) -> B
) = runBlocking(dispatcher) {
    pmapIndexed(block)
}

suspend fun <A, B> Iterable<A>.pmapNotNullIndexed(block: suspend (A, Int) -> B?): MutableList<B> =
    coroutineScope {
        mapIndexed { index, a -> async { block(a, index) } }.awaitAll()
    }.filterNotNull().toMutableList()

fun <A, B> Iterable<A>.pmapNotNullIndexed(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    block: suspend (A, Int) -> B?
) = runBlocking(dispatcher) {
    pmapNotNullIndexed(block)
}