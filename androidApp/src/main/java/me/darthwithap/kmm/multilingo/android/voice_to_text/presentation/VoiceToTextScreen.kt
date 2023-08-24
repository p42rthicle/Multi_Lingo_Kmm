@file:OptIn(ExperimentalAnimationApi::class, ExperimentalAnimationApi::class)

package me.darthwithap.kmm.multilingo.android.voice_to_text.presentation

import android.Manifest
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.darthwithap.kmm.multilingo.android.R
import me.darthwithap.kmm.multilingo.android.core.theme.LightBlue
import me.darthwithap.kmm.multilingo.android.voice_to_text.presentation.components.VoiceRecorderWaveDisplay
import me.darthwithap.kmm.multilingo.voice_to_text.presentation.RecorderDisplayState
import me.darthwithap.kmm.multilingo.voice_to_text.presentation.VoiceToTextEvent
import me.darthwithap.kmm.multilingo.voice_to_text.presentation.VoiceToTextState

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun VoiceToTextScreen(
  modifier: Modifier = Modifier,
  state: VoiceToTextState,
  languageCode: String,
  onVoiceResult: (String) -> Unit,
  onEvent: (VoiceToTextEvent) -> Unit
) {
  val context = LocalContext.current
  val recordAudioLauncher =
    rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
      onEvent(
        VoiceToTextEvent.PermissionsUpdated(
          isGranted = isGranted,
          isPermanentlyDenied = !isGranted && (context as ComponentActivity)
            .shouldShowRequestPermissionRationale(
              Manifest.permission.RECORD_AUDIO
            )
        )
      )
    }

  LaunchedEffect(key1 = recordAudioLauncher) {
    recordAudioLauncher.launch(Manifest.permission.RECORD_AUDIO)
  }

  Scaffold(
    floatingActionButton = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        FloatingActionButton(
          onClick = {
            if (state.displayState == RecorderDisplayState.DISPLAYING_RESULTS)
              state.spokenTextResult?.let {
                onVoiceResult(it)
              }
            else onEvent(VoiceToTextEvent.ToggleRecording(languageCode))
          },
          backgroundColor = MaterialTheme.colors.primary,
          modifier = Modifier.size(75.dp)
        ) {
          AnimatedContent(targetState = state.displayState) { displayState ->
            when (displayState) {
              RecorderDisplayState.SPEAKING -> {
                Icon(
                  modifier = Modifier.size(44.dp),
                  imageVector = Icons.Default.Close,
                  contentDescription = stringResource(id = R.string.stop_speaking)
                )
              }

              RecorderDisplayState.DISPLAYING_RESULTS -> {
                Icon(
                  modifier = Modifier.size(44.dp),
                  imageVector = Icons.Default.Check,
                  contentDescription = stringResource(id = R.string.use_recording)
                )
              }

              else -> {
                Icon(
                  imageVector = ImageVector.vectorResource(id = R.drawable.mic),
                  contentDescription = stringResource(id = R.string.record_audio),
                  modifier = Modifier.size(44.dp)
                )
              }
            }
          }
        }
        if (state.displayState == RecorderDisplayState.DISPLAYING_RESULTS) {
          IconButton(onClick = {
            onEvent(VoiceToTextEvent.ToggleRecording(languageCode))
          }) {
            Icon(
              imageVector = Icons.Rounded.Refresh,
              contentDescription = stringResource(id = R.string.record_again),
              tint = LightBlue
            )
          }
        }
      }
    },
    floatingActionButtonPosition = FabPosition.Center
  ) { paddingValues ->
    Column(
      modifier = modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        contentAlignment = Alignment.CenterStart
      ) {
        IconButton(
          modifier = Modifier.align(Alignment.CenterStart),
          onClick = { onEvent(VoiceToTextEvent.CloseScreen) }) {
          Icon(
            imageVector = Icons.Rounded.Close,
            contentDescription = stringResource(id = R.string.close)
          )
        }

        if (state.displayState == RecorderDisplayState.SPEAKING) {
          Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = R.string.listening),
            color = LightBlue,
            style = MaterialTheme.typography.body1
          )
        }
      }
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
          .padding(bottom = 80.dp)
          .weight(1f)
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        AnimatedContent(targetState = state.displayState) {
          when (state.displayState) {
            RecorderDisplayState.WAITING_TO_SPEAK -> {
              Text(
                text = stringResource(id = R.string.start_talking),
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Center
              )
            }

            RecorderDisplayState.SPEAKING -> {
              VoiceRecorderWaveDisplay(
                modifier = Modifier
                  .fillMaxWidth()
                  .height(90.dp),
                powerRatios = state.powerRatios
              )
            }

            RecorderDisplayState.DISPLAYING_RESULTS -> {
              Text(
                text = state.spokenTextResult ?: "",
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Center
              )
            }

            RecorderDisplayState.ERROR -> {
              Text(
                text = state.recordError ?: "Unknown Error",
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h2
              )
            }

            else -> Unit
          }
        }
      }
    }
  }
}