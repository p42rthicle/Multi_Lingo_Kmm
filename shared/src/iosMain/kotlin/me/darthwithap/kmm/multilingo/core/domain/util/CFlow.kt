package me.darthwithap.kmm.multilingo.core.domain.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

actual class CFlow<T> actual constructor(
  private val flow: Flow<T>
) : Flow<T> by flow {
  fun subscribe(
    coroutineScope: CoroutineScope,
    dispatcher: CoroutineDispatcher,
    onCollect: (T) -> Unit
  ): DisposableHandle {
    val job = coroutineScope.launch(dispatcher) {
      flow.collect(onCollect)
    }

    return DisposableHandle { job.cancel() }
  }
}