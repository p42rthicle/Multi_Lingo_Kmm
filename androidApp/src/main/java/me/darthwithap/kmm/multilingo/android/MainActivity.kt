package me.darthwithap.kmm.multilingo.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.darthwithap.kmm.multilingo.android.core.presentation.Routes
import me.darthwithap.kmm.multilingo.android.translate.presentation.AndroidTranslateViewModel
import me.darthwithap.kmm.multilingo.android.translate.presentation.TranslateScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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

@Composable
fun TranslateRoot() {
  val navController = rememberNavController()
  NavHost(navController = navController, startDestination = Routes.Translate) {
    composable(route = Routes.Translate) {
      val viewModel: AndroidTranslateViewModel = hiltViewModel()
      val state by viewModel.state.collectAsState()
      TranslateScreen(state = state, onEvent = viewModel::onEvent)
    }
  }
}