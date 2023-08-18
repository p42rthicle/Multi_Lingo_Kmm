package me.darthwithap.kmm.multilingo.translate.data.history

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import me.darthwithap.kmm.multilingo.core.domain.util.CFlow
import me.darthwithap.kmm.multilingo.core.domain.util.toCommon
import me.darthwithap.kmm.multilingo.database.TranslationDatabase
import me.darthwithap.kmm.multilingo.translate.domain.history.TranslationHistoryDataSource
import me.darthwithap.kmm.multilingo.translate.domain.history.TranslationHistoryItem

class SqlDelightTranslationHistoryDataSource(
  private val db: TranslationDatabase
) : TranslationHistoryDataSource {
  override fun getHistory(): CFlow<List<TranslationHistoryItem>> {
    return db.translateQueries.getHistory().asFlow().mapToList()
      .map { historyEntities ->
        historyEntities.map { it.toDomainModel() }
      }.toCommon()
  }

  override suspend fun insertHistoryItem(item: TranslationHistoryItem) {
    with(item) {
      db.translateQueries.insertTranslationHistoryEntity(
        id,
        fromLanguageCode,
        toLanguageCode,
        fromText,
        toText,
        timestamp ?: Clock.System.now().toEpochMilliseconds()
      )
    }
  }
}