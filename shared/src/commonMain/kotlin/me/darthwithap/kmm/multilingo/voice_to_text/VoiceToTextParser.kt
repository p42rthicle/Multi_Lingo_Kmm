package me.darthwithap.kmm.multilingo.voice_to_text

import me.darthwithap.kmm.multilingo.core.domain.util.CFlow
import me.darthwithap.kmm.multilingo.core.domain.util.CStateFlow

interface VoiceToTextParser {
  val state: CStateFlow<VoiceToTextParserState>
  fun startListening(languageCode: String)
  fun stopListening()
  fun cancel()
  fun reset()
}