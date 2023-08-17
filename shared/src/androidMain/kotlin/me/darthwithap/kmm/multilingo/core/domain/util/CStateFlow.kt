package me.darthwithap.kmm.multilingo.core.domain.util

import kotlinx.coroutines.flow.StateFlow

actual class CStateFlow<T> actual constructor(
  private val stateFlow: StateFlow<T>
) : StateFlow<T> by stateFlow