package me.darthwithap.kmm.multilingo.translate.presentation

import me.darthwithap.kmm.multilingo.core.presentation.UiLanguage
import me.darthwithap.kmm.multilingo.translate.domain.translate.TranslateError

data class TranslateState(
  val fromText: String = "",
  val toText: String? = null,
  val fromUiLanguage: UiLanguage = UiLanguage.byCode("en"),
  val toUiLanguage: UiLanguage = UiLanguage.byCode("hi"),
  val isTranslating: Boolean = false,
  val isChoosingFromLanguage: Boolean = false,
  val isChoosingToLanguage: Boolean = false,
  val error: TranslateError? = null,
  val historyList: List<UiTranslationHistoryItem> = emptyList()
)