package me.darthwithap.kmm.multilingo.translate.domain.translate

import me.darthwithap.kmm.multilingo.core.domain.language.Language

interface TranslateClient {
  suspend fun translate(
    fromLanguage: Language,
    fromText: String,
    toLanguage: Language
  ): String
}