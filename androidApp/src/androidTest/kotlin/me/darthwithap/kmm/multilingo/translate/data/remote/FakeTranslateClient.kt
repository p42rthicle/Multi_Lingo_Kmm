package me.darthwithap.kmm.multilingo.translate.data.remote

import me.darthwithap.kmm.multilingo.core.domain.language.Language
import me.darthwithap.kmm.multilingo.translate.domain.translate.TranslateClient

class FakeTranslateClient : TranslateClient {
  var translatedText = "test translation test"
  override suspend fun translate(
    fromLanguage: Language,
    fromText: String,
    toLanguage: Language
  ): String {
    return translatedText
  }
}