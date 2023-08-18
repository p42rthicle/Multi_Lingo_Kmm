package me.darthwithap.kmm.multilingo.translate.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import me.darthwithap.kmm.multilingo.core.domain.util.CStateFlow
import me.darthwithap.kmm.multilingo.core.domain.util.Resource
import me.darthwithap.kmm.multilingo.core.domain.util.toCommon
import me.darthwithap.kmm.multilingo.core.presentation.UiLanguage
import me.darthwithap.kmm.multilingo.translate.domain.history.TranslationHistoryDataSource
import me.darthwithap.kmm.multilingo.translate.domain.translate.Translate
import me.darthwithap.kmm.multilingo.translate.domain.translate.TranslateException

class TranslateViewModel(
  private val translateUseCase: Translate,
  private val historyDataSource: TranslationHistoryDataSource,
  private val coroutineScope: CoroutineScope? = null
) {
  private val viewModelScope: CoroutineScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

  private val _state = MutableStateFlow(TranslateState())
  val state: CStateFlow<TranslateState> =
    combine(_state, historyDataSource.getHistory()) { state, history ->
      // emitted whenever either flows' give new value
      if (state.historyList != history) {
        state.copy(historyList = history.mapNotNull {
          UiTranslationHistoryItem(
            it.id ?: return@mapNotNull null,
            it.fromText,
            it.toText,
            UiLanguage.byCode(it.fromLanguageCode),
            UiLanguage.byCode(it.toLanguageCode),
            it.timestamp ?: return@mapNotNull null
          )
        })
      } else state
    }.stateIn(
      viewModelScope, SharingStarted.WhileSubscribed(4000), TranslateState()
    ).toCommon()

  private var translateJob: Job? = null

  fun onEvent(event: TranslateEvent) {
    when (event) {
      TranslateEvent.Translate -> {
        translate(_state.value)
      }

      TranslateEvent.EditTranslation -> {
        if (_state.value.toText != null) {
          _state.update {
            it.copy(
              toText = null,
              isTranslating = false
            )
          }
        }
      }

      is TranslateEvent.FromLanguageChosen -> {
        _state.update {
          it.copy(
            isChoosingFromLanguage = false,
            fromUiLanguage = event.fromUiLanguage
          )
        }
      }

      TranslateEvent.FromLanguageDropDownOpened -> {
        _state.update {
          it.copy(isChoosingFromLanguage = true)
        }
      }

      is TranslateEvent.HistoryItemChosen -> {
        translateJob?.cancel()
        _state.update {
          it.copy(
            fromText = event.historyItem.fromText,
            toText = event.historyItem.toText,
            fromUiLanguage = event.historyItem.fromUiLanguage,
            toUiLanguage = event.historyItem.toUiLanguage,
            isTranslating = false
          )
        }
      }

      TranslateEvent.OnErrorSeen -> {
        _state.update { it.copy(error = null) }
      }

      TranslateEvent.StopChoosingLanguage -> {
        _state.update {
          it.copy(
            isChoosingFromLanguage = false,
            isChoosingToLanguage = false
          )
        }
      }

      TranslateEvent.StopTranslation -> {
        _state.update {
          it.copy(isTranslating = false, fromText = "", toText = null)
        }
      }

      is TranslateEvent.OnVoiceResultSubmitted -> {
        _state.update {
          it.copy(
            fromText = event.voiceResult ?: it.fromText,
            isTranslating = if (event.voiceResult != null) false else it.isTranslating,
            toText = if (event.voiceResult != null) null else it.toText,
          )
        }
      }

      TranslateEvent.SwapLanguages -> {
        val fromLanguage = _state.value.toUiLanguage
        val toLanguage = _state.value.fromUiLanguage
        val fromText = _state.value.toText ?: ""
        val toText = _state.value.toText?.let { _state.value.fromText }
        _state.update {
          it.copy(
            fromUiLanguage = fromLanguage, toUiLanguage = toLanguage,
            fromText = fromText, toText = toText
          )
        }
      }

      TranslateEvent.ToLanguageDropDownOpened -> {
        _state.update {
          it.copy(isChoosingToLanguage = true)
        }
      }

      is TranslateEvent.ToLanguageChosen -> {
        val newState = _state.updateAndGet {
          it.copy(toUiLanguage = event.toUiLanguage)
        }
        translate(newState)
      }

      is TranslateEvent.TranslationTextChanged -> {
        _state.update {
          it.copy(fromText = event.fromText)
        }
      }

      else -> {}
    }
  }

  private fun translate(state: TranslateState) {
    if (state.isTranslating || state.fromText.isBlank()) {
      return
    }
    translateJob = viewModelScope.launch {
      _state.update { it.copy(isTranslating = true) }
      val translateResult: Resource<String> = translateUseCase(
        state.fromText,
        state.fromUiLanguage.language,
        state.toUiLanguage.language
      )

      when (translateResult) {
        is Resource.Success -> {
          _state.update {
            it.copy(isTranslating = false, toText = translateResult.data)
          }
        }

        is Resource.Error -> {
          _state.update {
            it.copy(
              isTranslating = false,
              error = (translateResult.throwable as? TranslateException)?.error
            )
          }
        }
      }
    }
  }

}