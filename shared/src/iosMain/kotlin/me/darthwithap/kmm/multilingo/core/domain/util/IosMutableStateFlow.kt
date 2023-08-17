package me.darthwithap.kmm.multilingo.core.domain.util

import kotlinx.coroutines.flow.MutableStateFlow

class IosMutableStateFlow<T>(
  initialValue: T
) : CMutableStateFlow<T>(MutableStateFlow(initialValue))