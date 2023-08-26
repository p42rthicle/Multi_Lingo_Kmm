package me.darthwithap.kmm.multilingo.presentation

import android.Manifest
import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.GrantPermissionRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import me.darthwithap.kmm.multilingo.android.MainActivity
import me.darthwithap.kmm.multilingo.android.R
import me.darthwithap.kmm.multilingo.android.di.AppModule
import me.darthwithap.kmm.multilingo.android.di.VoiceToTextModule
import me.darthwithap.kmm.multilingo.translate.data.remote.FakeTranslateClient
import me.darthwithap.kmm.multilingo.translate.domain.translate.TranslateClient
import me.darthwithap.kmm.multilingo.voice_to_text.VoiceToTextParser
import me.darthwithap.kmm.multilingo.voice_to_text.data.FakeVoiceToTextParser
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(AppModule::class, VoiceToTextModule::class)
class VoiceToTextE2E {

  @get:Rule
  val composeRule = createAndroidComposeRule<MainActivity>()

  @get:Rule
  val hiltRule = HiltAndroidRule(this)

  @get:Rule
  val permissionRule = GrantPermissionRule.grant(Manifest.permission.RECORD_AUDIO)

  @Inject
  lateinit var fakeVoiceParser: VoiceToTextParser

  @Inject
  lateinit var fakeTranslateClient: TranslateClient

  @Before
  fun setUp() {
    hiltRule.inject()
  }

  @Test
  fun testRecordAndTranslate() = runBlocking<Unit> {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val parser = fakeVoiceParser as FakeVoiceToTextParser
    val translate = fakeTranslateClient as FakeTranslateClient

    composeRule
      .onNodeWithContentDescription(context.getString(R.string.record_audio))
      .performClick()

    composeRule
      .onNodeWithContentDescription(context.getString(R.string.record_audio))
      .performClick()

    composeRule
      .onNodeWithContentDescription(context.getString(R.string.stop_speaking))
      .performClick()

    composeRule
      .onNodeWithText(parser.voiceResult)
      .assertIsDisplayed()

    composeRule
      .onNodeWithContentDescription(context.getString(R.string.use_recording))
      .performClick()

    composeRule
      .onNodeWithText(parser.voiceResult)
      .assertIsDisplayed()

    composeRule
      .onNodeWithText(context.getString(R.string.translate), ignoreCase = true)
      .performClick()

    composeRule
      .onNodeWithText(parser.voiceResult)
      .assertIsDisplayed()

    composeRule
      .onNodeWithText(translate.translatedText)
      .assertIsDisplayed()
  }
}