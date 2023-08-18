package me.darthwithap.kmm.multilingo.translate.domain.translate

import me.darthwithap.kmm.multilingo.core.domain.language.Language
import me.darthwithap.kmm.multilingo.core.domain.util.Resource
import me.darthwithap.kmm.multilingo.translate.domain.history.TranslationHistoryDataSource
import me.darthwithap.kmm.multilingo.translate.domain.history.TranslationHistoryItem

class Translate(
  private val translateClient: TranslateClient,
  private val historyDataSource: TranslationHistoryDataSource
) {
  suspend operator fun invoke(
    fromText: String,
    fromLanguage: Language,
    toLanguage: Language
  ): Resource<String> {
    return try {
      val translatedText = translateClient.translate(fromLanguage, fromText, toLanguage)
      historyDataSource.insertHistoryItem(
        TranslationHistoryItem(
          null, fromLanguage.languageCode, fromText, toLanguage.languageCode, translatedText
        )
      )
      Resource.Success(translatedText)
    } catch (e: TranslateException) {
      e.printStackTrace()
      Resource.Error(e)
    }
  }
}