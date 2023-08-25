package me.darthwithap.kmm.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.darthwithap.kmm.multilingo.translate.data.local.FakeTranslationHistoryDataSource
import me.darthwithap.kmm.multilingo.translate.data.remote.FakeTranslateClient
import me.darthwithap.kmm.multilingo.translate.domain.history.TranslationHistoryDataSource
import me.darthwithap.kmm.multilingo.translate.domain.translate.Translate
import me.darthwithap.kmm.multilingo.translate.domain.translate.TranslateClient
import me.darthwithap.kmm.multilingo.voice_to_text.data.FakeVoiceToTextParser
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

  @Provides
  @Singleton
  fun provideFakeTranslateClient(): FakeTranslateClient {
    return FakeTranslateClient()
  }

  @Provides
  @Singleton
  fun provideFakeTranslationHistoryDatasource(): FakeTranslationHistoryDataSource {
    return FakeTranslationHistoryDataSource()
  }

  @Provides
  @Singleton
  fun provideTranslateUseCase(
    translateClient: TranslateClient,
    historyDataSource: TranslationHistoryDataSource
  ): Translate {
    return Translate(translateClient, historyDataSource)
  }

  @Provides
  @Singleton
  fun provideFakeVoiceToTextParser(): FakeVoiceToTextParser {
    return FakeVoiceToTextParser()
  }
}