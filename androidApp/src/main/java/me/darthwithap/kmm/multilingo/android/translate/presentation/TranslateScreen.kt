package me.darthwithap.kmm.multilingo.android.translate.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.darthwithap.kmm.multilingo.android.translate.presentation.components.LanguageDropDown
import me.darthwithap.kmm.multilingo.android.translate.presentation.components.SwapLanguagesButton
import me.darthwithap.kmm.multilingo.translate.presentation.TranslateEvent
import me.darthwithap.kmm.multilingo.translate.presentation.TranslateState

@Composable
fun TranslateScreen(
  state: TranslateState,
  onEvent: (TranslateEvent) -> Unit
) {
  Scaffold(
    floatingActionButton = {

    }
  ) { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          LanguageDropDown(
            language = state.fromUiLanguage,
            isOpen = state.isChoosingFromLanguage,
            onClick = { onEvent(TranslateEvent.FromLanguageDropDownOpened) },
            onDismiss = { onEvent(TranslateEvent.StopChoosingLanguage) },
            onLanguageSelected = {
              onEvent(TranslateEvent.FromLanguageChosen(it))
            }
          )
          Spacer(modifier = Modifier.weight(1f))
          SwapLanguagesButton {
            onEvent(TranslateEvent.SwapLanguages)
          }
          Spacer(modifier = Modifier.weight(1f))
          LanguageDropDown(
            language = state.toUiLanguage,
            isOpen = state.isChoosingToLanguage,
            onClick = { onEvent(TranslateEvent.ToLanguageDropDownOpened) },
            onDismiss = { onEvent(TranslateEvent.StopChoosingLanguage) },
            onLanguageSelected = {
              onEvent(TranslateEvent.ToLanguageChosen(it))
            }
          )
        }
      }
    }
  }
}