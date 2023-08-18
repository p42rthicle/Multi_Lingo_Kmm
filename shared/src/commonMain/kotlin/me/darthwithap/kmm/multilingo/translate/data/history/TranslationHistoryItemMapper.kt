package me.darthwithap.kmm.multilingo.translate.data.history

import database.TranslationHistoryEntity
import me.darthwithap.kmm.multilingo.translate.domain.history.TranslationHistoryItem

fun TranslationHistoryEntity.toDomainModel() =
  TranslationHistoryItem(
    id = id,
    fromLanguageCode = fromLanguageCode,
    fromText = fromText,
    toLanguageCode = toLanguageCode,
    toText = toText,
    timestamp = timestamp
  )