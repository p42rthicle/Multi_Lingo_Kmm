@file:OptIn(ExperimentalComposeUiApi::class)

package me.darthwithap.kmm.multilingo.android.translate.presentation

import android.os.Build
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import me.darthwithap.kmm.multilingo.android.R
import me.darthwithap.kmm.multilingo.android.translate.presentation.components.LanguageDropDown
import me.darthwithap.kmm.multilingo.android.translate.presentation.components.SwapLanguagesButton
import me.darthwithap.kmm.multilingo.android.translate.presentation.components.TranslateTextField
import me.darthwithap.kmm.multilingo.android.translate.presentation.components.TranslationHistoryItem
import me.darthwithap.kmm.multilingo.android.translate.presentation.components.rememberTextToSpeech
import me.darthwithap.kmm.multilingo.translate.presentation.TranslateEvent
import me.darthwithap.kmm.multilingo.translate.presentation.TranslateState
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TranslateScreen(
  state: TranslateState,
  onEvent: (TranslateEvent) -> Unit
) {
  val context = LocalContext.current

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
      item {
        val clipboardManager = LocalClipboardManager.current
        val keyboardController = LocalSoftwareKeyboardController.current
        val tts = rememberTextToSpeech()
        TranslateTextField(
          modifier = Modifier.fillMaxWidth(),
          fromText = state.fromText,
          toText = state.toText,
          isTranslating = state.isTranslating,
          fromUiLanguage = state.fromUiLanguage,
          toUiLanguage = state.toUiLanguage,
          onTranslateClick = {
            keyboardController?.hide()
            onEvent(TranslateEvent.Translate)
          },
          onTextChanged = { onEvent(TranslateEvent.TranslationTextChanged(it)) },
          onCopyClick = {
            clipboardManager.setText(buildAnnotatedString {
              append(it)
            })
            Toast.makeText(
              context, context.getString(R.string.copied_to_clipboard), Toast.LENGTH_SHORT
            ).show()
          },
          onCloseClick = { onEvent(TranslateEvent.StopTranslation) },
          onSpeakerClick = {
            val locale = state.toUiLanguage.toLocale() ?: Locale.ENGLISH
            tts.language = locale
            tts.speak(
              state.toText, TextToSpeech.QUEUE_FLUSH, null, null
            )
          },
          onTextFieldClick = { onEvent(TranslateEvent.EditTranslation) })
      }
      item {
        if (state.historyList.isNotEmpty()) {
          Text(text = stringResource(id = R.string.history), style = MaterialTheme.typography.h3)
        }
      }
      items(state.historyList) { historyItem ->
        TranslationHistoryItem(modifier = Modifier.fillMaxWidth(), historyItem = historyItem) {
          onEvent(TranslateEvent.HistoryItemChosen(historyItem))
        }
      }
    }
  }
}