package me.darthwithap.kmm.multilingo.di

import me.darthwithap.kmm.multilingo.database.TranslationDatabase
import me.darthwithap.kmm.multilingo.translate.data.history.SqlDelightTranslationHistoryDataSource
import me.darthwithap.kmm.multilingo.translate.data.local.DatabaseDriverFactory
import me.darthwithap.kmm.multilingo.translate.data.remote.HttpClientFactory
import me.darthwithap.kmm.multilingo.translate.data.translate.KtorTranslateClient
import me.darthwithap.kmm.multilingo.translate.domain.history.TranslationHistoryDataSource
import me.darthwithap.kmm.multilingo.translate.domain.translate.Translate
import me.darthwithap.kmm.multilingo.translate.domain.translate.TranslateClient

class AppModule {
  val historyDataSource: TranslationHistoryDataSource by lazy {
    SqlDelightTranslationHistoryDataSource(
      TranslationDatabase(
        DatabaseDriverFactory().create()
      )
    )
  }
  private val translateClient: TranslateClient by lazy {
    KtorTranslateClient(
      HttpClientFactory().create()
    )
  }
  val translateUseCase: Translate by lazy {
    Translate(translateClient, historyDataSource)
  }
}