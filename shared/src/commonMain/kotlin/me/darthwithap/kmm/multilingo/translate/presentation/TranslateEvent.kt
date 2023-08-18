package me.darthwithap.kmm.multilingo.translate.presentation

import me.darthwithap.kmm.multilingo.core.presentation.UiLanguage

sealed class TranslateEvent {
  object Translate : TranslateEvent()
  object StopTranslation : TranslateEvent()
  object EditTranslation : TranslateEvent()
  object FromLanguageDropDownOpened : TranslateEvent()
  object ToLanguageDropDownOpened : TranslateEvent()
  data class FromLanguageChosen(val fromUiLanguage: UiLanguage) : TranslateEvent()
  data class ToLanguageChosen(val toUiLanguage: UiLanguage) : TranslateEvent()
  object StopChoosingLanguage : TranslateEvent()
  object SwapLanguages : TranslateEvent()
  data class TranslationTextChanged(val fromText: String) : TranslateEvent()
  data class HistoryItemChosen(val historyItem: UiTranslationHistoryItem) : TranslateEvent()
  object RecordVoiceAudio : TranslateEvent()
  data class OnVoiceResultSubmitted(val voiceResult: String?) : TranslateEvent()
  object OnErrorSeen : TranslateEvent()

}