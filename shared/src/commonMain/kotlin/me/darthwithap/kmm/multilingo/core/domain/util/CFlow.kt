package me.darthwithap.kmm.multilingo.core.domain.util

import kotlinx.coroutines.flow.Flow

expect class CFlow<T>(flow: Flow<T>) : Flow<T>

fun <T> Flow<T>.toCFlow() = CFlow(this)