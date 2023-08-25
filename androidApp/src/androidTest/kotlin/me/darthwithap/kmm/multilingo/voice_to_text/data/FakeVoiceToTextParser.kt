package me.darthwithap.kmm.multilingo.voice_to_text.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import me.darthwithap.kmm.multilingo.core.domain.util.CStateFlow
import me.darthwithap.kmm.multilingo.core.domain.util.toCStateFlow
import me.darthwithap.kmm.multilingo.voice_to_text.VoiceToTextParser
import me.darthwithap.kmm.multilingo.voice_to_text.VoiceToTextParserState

class FakeVoiceToTextParser : VoiceToTextParser {
  private val _state = MutableStateFlow(VoiceToTextParserState())
  override val state: CStateFlow<VoiceToTextParserState>
    get() = _state.toCStateFlow()

  var voiceResult = "test voice result"

  override fun startListening(languageCode: String) {
    _state.update {
      it.copy(
        transcribedResult = "",
        isSpeaking = true
      )
    }
  }

  override fun stopListening() {
    _state.update { it.copy(
      transcribedResult = voiceResult,
      isSpeaking = false
    ) }
  }

  override fun cancel() {}

  override fun reset() {
    _state.update { VoiceToTextParserState() }
  }
}