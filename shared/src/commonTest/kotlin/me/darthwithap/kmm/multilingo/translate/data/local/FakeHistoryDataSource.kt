package me.darthwithap.kmm.multilingo.translate.data.local

import kotlinx.coroutines.flow.MutableStateFlow
import me.darthwithap.kmm.multilingo.core.domain.util.CFlow
import me.darthwithap.kmm.multilingo.core.domain.util.toCFlow
import me.darthwithap.kmm.multilingo.translate.domain.history.TranslationHistoryDataSource
import me.darthwithap.kmm.multilingo.translate.domain.history.TranslationHistoryItem

class FakeTranslationHistoryDataSource: TranslationHistoryDataSource {

  private val _data = MutableStateFlow<List<TranslationHistoryItem>>(emptyList())
  override fun getHistory(): CFlow<List<TranslationHistoryItem>> {
    return _data.toCFlow()
  }

  override suspend fun insertHistoryItem(item: TranslationHistoryItem) {
    _data.value += item
  }
}