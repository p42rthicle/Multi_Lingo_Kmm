package me.darthwithap.kmm.multilingo.translate.presentation

import me.darthwithap.kmm.multilingo.core.presentation.UiLanguage

data class UiTranslationHistoryItem(
  val id: Long,
  val fromText: String,
  val toText: String,
  val fromUiLanguage: UiLanguage,
  val toUiLanguage: UiLanguage,
  val timestamp: Long
)
