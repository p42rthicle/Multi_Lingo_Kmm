package me.darthwithap.kmm.multilingo.core.domain.util

import kotlinx.coroutines.DisposableHandle

interface IosDisposableHandle : DisposableHandle

fun IosDisposableHandle(onDispose: () -> Unit): IosDisposableHandle {
  return IosDisposableHandle {
    onDispose()
  }
}