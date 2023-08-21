package me.darthwithap.kmm.multilingo.android.di

import android.app.Application
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import me.darthwithap.kmm.multilingo.database.TranslationDatabase
import me.darthwithap.kmm.multilingo.translate.data.history.SqlDelightTranslationHistoryDataSource
import me.darthwithap.kmm.multilingo.translate.data.local.DatabaseDriverFactory
import me.darthwithap.kmm.multilingo.translate.data.remote.HttpClientFactory
import me.darthwithap.kmm.multilingo.translate.data.translate.KtorTranslateClient
import me.darthwithap.kmm.multilingo.translate.domain.history.TranslationHistoryDataSource
import me.darthwithap.kmm.multilingo.translate.domain.translate.Translate
import me.darthwithap.kmm.multilingo.translate.domain.translate.TranslateClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
  @Provides
  @Singleton
  fun provideHttpClient(): HttpClient {
    return HttpClientFactory().create()
  }

  @Provides
  @Singleton
  fun provideTranslateClient(httpClient: HttpClient): TranslateClient {
    return KtorTranslateClient(httpClient)
  }

  @Provides
  @Singleton
  fun provideDatabaseDriver(app: Application): SqlDriver {
    return DatabaseDriverFactory(app.applicationContext).create()
  }

  @Provides
  @Singleton
  fun provideHistoryDatasource(driver: SqlDriver): TranslationHistoryDataSource {
    return SqlDelightTranslationHistoryDataSource(TranslationDatabase(driver))
  }

  @Provides
  @Singleton
  fun provideTranslateUseCase(
    client: TranslateClient,
    dataSource: TranslationHistoryDataSource
  ): Translate {
    return Translate(client, dataSource)
  }
}