package me.darthwithap.kmm.multilingo.voice_to_text.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.darthwithap.kmm.multilingo.core.domain.util.CStateFlow
import me.darthwithap.kmm.multilingo.core.domain.util.toCStateFlow
import me.darthwithap.kmm.multilingo.voice_to_text.VoiceToTextParser

class VoiceToTextViewModel(
  private val parser: VoiceToTextParser,
  coroutineScope: CoroutineScope? = null
) {
  private val viewModelScope: CoroutineScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

  private val _state: MutableStateFlow<VoiceToTextState> = MutableStateFlow(VoiceToTextState())
  val state: CStateFlow<VoiceToTextState> = _state.combine(parser.state) { state, parserResult ->
    state.copy(
      spokenTextResult = parserResult.transcribedResult,
      recordError = if (state.canRecord) parserResult.error else "Can't record without permission",
      displayState = when {
        parserResult.error != null || !state.canRecord -> RecorderDisplayState.ERROR
        parserResult.isSpeaking -> RecorderDisplayState.SPEAKING
        parserResult.transcribedResult.isNotBlank() && !parserResult.isSpeaking -> RecorderDisplayState.DISPLAYING_RESULTS
        else -> RecorderDisplayState.WAITING_TO_SPEAK
      }
    )
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(4000L), VoiceToTextState())
    .toCStateFlow()

  init {
    viewModelScope.launch {
      while (true) {
        if (state.value.displayState == RecorderDisplayState.SPEAKING) {
          _state.update { it.copy(powerRatios = it.powerRatios + parser.state.value.powerRatio) }
        }
        delay(20L)
      }
    }
  }

  private fun toggleListening(languageCode: String) {
    _state.update { it.copy(powerRatios = emptyList()) }
    parser.cancel()
    if (parser.state.value.isSpeaking) {
      parser.stopListening()
    } else if (_state.value.canRecord) {
      parser.startListening(languageCode)
    }
  }

  fun onEvent(event: VoiceToTextEvent) {
    when (event) {
      VoiceToTextEvent.CloseScreen -> {
        parser.stopListening()
      }
      is VoiceToTextEvent.PermissionsUpdated -> {
        _state.update { it.copy(canRecord = event.isGranted) }
      }

      VoiceToTextEvent.Reset -> {
        parser.reset()
        _state.update { VoiceToTextState() }
      }

      is VoiceToTextEvent.ToggleRecording -> {
        toggleListening(event.languageCode)
      }
    }
  }
}