package me.darthwithap.kmm.multilingo.core.domain.util

import kotlinx.coroutines.flow.StateFlow

expect class CStateFlow<T>(stateFlow: StateFlow<T>) : StateFlow<T>

fun <T> StateFlow<T>.toCommon() = CStateFlow<T>(this)