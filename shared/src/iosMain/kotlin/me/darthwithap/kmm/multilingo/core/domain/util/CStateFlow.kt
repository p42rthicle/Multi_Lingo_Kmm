package me.darthwithap.kmm.multilingo.core.domain.util

import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow

actual open class CStateFlow<T> actual constructor(
  private val stateFlow: StateFlow<T>
) : CFlow<T>(stateFlow), StateFlow<T> {
  override val replayCache: List<T>
    get() = stateFlow.replayCache
  override val value: T
    get() = stateFlow.value

  override suspend fun collect(collector: FlowCollector<T>): Nothing {
    stateFlow.collect(collector)
  }

}