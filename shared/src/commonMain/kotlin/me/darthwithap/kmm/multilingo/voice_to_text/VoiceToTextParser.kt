package me.darthwithap.kmm.multilingo.voice_to_text

import me.darthwithap.kmm.multilingo.core.domain.util.CFlow

interface VoiceToTextParser {
  val state: CFlow<VoiceToTextParserState>
  fun startListening(languageCode: String)
  fun stopListening()
  fun cancel()
  fun reset()
}