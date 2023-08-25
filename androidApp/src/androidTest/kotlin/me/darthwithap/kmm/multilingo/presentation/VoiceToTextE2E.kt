package me.darthwithap.kmm.multilingo.presentation

import android.content.Context
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import me.darthwithap.kmm.multilingo.android.MainActivity
import me.darthwithap.kmm.multilingo.android.R
import me.darthwithap.kmm.multilingo.android.di.AppModule
import me.darthwithap.kmm.multilingo.android.di.VoiceToTextModule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class, VoiceToTextModule::class)
class VoiceToTextE2E {

  @get:Rule
  val composeRule = createAndroidComposeRule<MainActivity>()

  @get:Rule
  val hiltRule = HiltAndroidRule(this)

  @Before
  fun setUp() {
    hiltRule.inject()
  }

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