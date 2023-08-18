package me.darthwithap.kmm.multilingo.translate.domain.history

data class TranslationHistoryItem(
  val id: Long?,
  val fromLanguageCode: String,
  val fromText: String,
  val toLanguageCode: String,
  val toText: String,
  val timestamp: Long
)
