package me.darthwithap.kmm.multilingo.core.domain.util

import kotlinx.coroutines.flow.Flow

actual class CFlow<T> actual constructor(
  private val flow: Flow<T>
) : Flow<T> by flow