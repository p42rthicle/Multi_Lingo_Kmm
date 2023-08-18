package me.darthwithap.kmm.multilingo.android.translate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import me.darthwithap.kmm.multilingo.translate.domain.history.TranslationHistoryDataSource
import me.darthwithap.kmm.multilingo.translate.domain.translate.Translate
import me.darthwithap.kmm.multilingo.translate.presentation.TranslateEvent
import me.darthwithap.kmm.multilingo.translate.presentation.TranslateViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidTranslateViewModel @Inject constructor(
  private val translateUseCase: Translate,
  private val historyDataSource: TranslationHistoryDataSource,
) : ViewModel() {
  private val viewModel: TranslateViewModel by lazy {
    TranslateViewModel(
      translateUseCase, historyDataSource, viewModelScope
    )
  }

  val state = viewModel.state

  fun onEvent(event: TranslateEvent) {
    viewModel.onEvent(event)
  }
}