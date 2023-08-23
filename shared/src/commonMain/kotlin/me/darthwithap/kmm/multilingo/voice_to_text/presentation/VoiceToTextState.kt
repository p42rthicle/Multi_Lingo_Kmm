package me.darthwithap.kmm.multilingo.voice_to_text.presentation

data class VoiceToTextState(
  val powerRatios: List<Float> = emptyList(),
  val spokenTextResult: String? = null,
  val canRecord: Boolean = false,
  val recordError: String? = null,
  val displayState: RecorderDisplayState = RecorderDisplayState.WAITING_TO_SPEAK
)
