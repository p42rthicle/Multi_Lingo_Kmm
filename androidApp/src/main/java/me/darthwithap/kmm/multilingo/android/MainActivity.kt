package me.darthwithap.kmm.multilingo.android

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import me.darthwithap.kmm.multilingo.android.core.presentation.Routes
import me.darthwithap.kmm.multilingo.android.translate.presentation.AndroidTranslateViewModel
import me.darthwithap.kmm.multilingo.android.translate.presentation.TranslateScreen
import me.darthwithap.kmm.multilingo.core.domain.language.Language
import me.darthwithap.kmm.multilingo.translate.presentation.TranslateEvent

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  @RequiresApi(Build.VERSION_CODES.O)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MultiLingoTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colors.background
        ) {
          TranslateRoot()
        }
      }
    }
  }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TranslateRoot() {
  val navController = rememberNavController()
  NavHost(navController = navController, startDestination = Routes.Translate) {
    composable(route = Routes.Translate) {
      val viewModel: AndroidTranslateViewModel = hiltViewModel()
      val state by viewModel.state.collectAsState()
      TranslateScreen(state = state, onEvent = {
        when (it) {
          is TranslateEvent.RecordVoiceAudio -> {
            navController.navigate(Routes.VoiceToText + "/${state.fromUiLanguage.language.languageCode}")
          }

          else -> viewModel.onEvent(it)
        }
      })
    }
    composable(
      route = Routes.VoiceToText + "/{languageCode}",
      arguments = listOf(navArgument("languageCode") {
        type = NavType.StringType
        defaultValue = Language.ENGLISH.languageCode
      })
    ) {
      Text(text = "Voice to text screen")
    }
  }
}