package me.darthwithap.kmm.multilingo.voice_to_text.presentation

sealed class VoiceToTextEvent {
  object CloseScreen : VoiceToTextEvent()
  data class PermissionsUpdated(
    val isGranted: Boolean, val isPermanentlyDenied: Boolean
  ) :
    VoiceToTextEvent()
  data class ToggleRecording(val languageCode: String) : VoiceToTextEvent()
  object Reset : VoiceToTextEvent()
}