package me.darthwithap.kmm.multilingo.voice_to_text

data class VoiceToTextParserState(
  val transcribedResult: String = "",
  val error: String? = null,
  val powerRatio: Float = 0f,
  val isSpeaking: Boolean = false
)
