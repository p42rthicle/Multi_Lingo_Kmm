package me.darthwithap.kmm.multilingo.translate.domain.history

import me.darthwithap.kmm.multilingo.core.domain.util.CFlow

interface TranslationHistoryDataSource {
  fun getHistory(): CFlow<List<TranslationHistoryItem>>
  suspend fun insertHistoryItem(item: TranslationHistoryItem)
}