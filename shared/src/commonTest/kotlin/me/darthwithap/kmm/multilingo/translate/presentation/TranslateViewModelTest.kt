package me.darthwithap.kmm.multilingo.translate.presentation

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import me.darthwithap.kmm.multilingo.core.presentation.UiLanguage
import me.darthwithap.kmm.multilingo.translate.data.local.FakeTranslationHistoryDataSource
import me.darthwithap.kmm.multilingo.translate.data.remote.FakeTranslateClient
import me.darthwithap.kmm.multilingo.translate.domain.history.TranslationHistoryItem
import me.darthwithap.kmm.multilingo.translate.domain.translate.Translate
import kotlin.test.BeforeTest
import kotlin.test.Test

class TranslateViewModelTest {
  private lateinit var viewModel: TranslateViewModel
  private lateinit var translateClient: FakeTranslateClient
  private lateinit var historyDataSource: FakeTranslationHistoryDataSource

  @BeforeTest
  fun setUp() {
    translateClient = FakeTranslateClient()
    historyDataSource = FakeTranslationHistoryDataSource()
    val translateUseCase = Translate(translateClient, historyDataSource)

    viewModel =
      TranslateViewModel(
        translateUseCase,
        historyDataSource,
        CoroutineScope(Dispatchers.Default)
      )
  }

  @Test
  fun `TranslateState and History items are combined properly into one flow`() = runBlocking {
    viewModel.state.test {
      val initialState = awaitItem()
      assertThat(initialState).isEqualTo(TranslateState())

      val historyItem = TranslationHistoryItem(
        id = 0,
        fromText = "from text",
        fromLanguageCode = "en",
        toLanguageCode = "hi",
        toText = "to text",
        timestamp = 100L
      )
      historyDataSource.insertHistoryItem(historyItem)

      val state = awaitItem()

      val expected = UiTranslationHistoryItem(
        id = historyItem.id!!,
        fromText = historyItem.fromText,
        toText = historyItem.toText,
        fromUiLanguage = UiLanguage.byCode(historyItem.fromLanguageCode),
        toUiLanguage = UiLanguage.byCode(historyItem.toLanguageCode),
        timestamp = historyItem.timestamp ?: 100L
      )
      assertThat(state.historyList.first()).isEqualTo(expected)
    }
  }
}