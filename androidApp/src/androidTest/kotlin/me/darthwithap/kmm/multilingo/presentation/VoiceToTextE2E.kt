package me.darthwithap.kmm.multilingo.presentation

import android.content.Context
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import me.darthwithap.kmm.multilingo.android.MainActivity
import me.darthwithap.kmm.multilingo.android.R
import org.junit.Rule
import org.junit.Test

class VoiceToTextE2E {

  @get:Rule
  val composeRule = createAndroidComposeRule<MainActivity>()

  @Test
  fun testRecordAndTranslate() = runBlocking {
    val context = ApplicationProvider.getApplicationContext<Context>()
    composeRule
      .onNodeWithContentDescription(context.getString(R.string.record_audio))
      .performClick()

    composeRule
      .onNodeWithContentDescription(context.getString(R.string.record_audio))
      .performClick()

    composeRule
      .onNodeWithContentDescription(context.getString(R.string.stop_speaking))
      .performClick()


  }
}