package me.darthwithap.kmm.multilingo.core.domain.util

import kotlinx.coroutines.flow.MutableStateFlow

expect class CMutableStateFlow<T>(mutableStateFlow: MutableStateFlow<T>) : MutableStateFlow<T>

fun <T> MutableStateFlow<T>.toCommon() = CMutableStateFlow<T>(this)