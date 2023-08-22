@file:OptIn(ExperimentalAnimationApi::class)

package me.darthwithap.kmm.multilingo.android.translate.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import me.darthwithap.kmm.multilingo.android.R
import me.darthwithap.kmm.multilingo.android.core.theme.LightBlue
import me.darthwithap.kmm.multilingo.core.presentation.UiLanguage

@Composable
fun TranslateTextField(
  modifier: Modifier = Modifier,
  fromText: String,
  toText: String? = null,
  isTranslating: Boolean = false,
  fromUiLanguage: UiLanguage,
  toUiLanguage: UiLanguage,
  onTranslateClick: () -> Unit,
  onTextChanged: (String) -> Unit,
  onCopyClick: (String) -> Unit,
  onCloseClick: () -> Unit,
  onSpeakerClick: () -> Unit,
  onTextFieldClick: () -> Unit
) {
  Box(
    modifier = modifier
      .shadow(elevation = 4.dp, shape = RoundedCornerShape(20.dp))
      .clip(
        RoundedCornerShape(18.dp)
      )
      .gradientSurface()
      .clickable(onClick = onTextFieldClick)
      .padding(16.dp)
  ) {
    AnimatedContent(targetState = toText) { toText ->
      val isIdleState = toText == null || isTranslating
      if (isIdleState) {
        IdleTranslateTextField(
          modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f),
          fromText = fromText,
          isTranslating = isTranslating,
          onTextChanged = onTextChanged,
          onTranslateClick = onTranslateClick
        )
      } else {
        TranslatedTextField(
          modifier = Modifier
            .fillMaxWidth(),
          fromText = fromText,
          toText = toText ?: "",
          fromUiLanguage = fromUiLanguage,
          toUiLanguage = toUiLanguage,
          onCopyClick = onCopyClick,
          onCloseClick = onCloseClick,
          onSpeakerClick = onSpeakerClick
        )
      }
    }
  }
}

@Composable
private fun IdleTranslateTextField(
  modifier: Modifier,
  fromText: String,
  isTranslating: Boolean,
  onTextChanged: (String) -> Unit,
  onTranslateClick: () -> Unit,
) {
  var isFocused by remember { mutableStateOf(false) }
  Box(modifier = modifier) {
    BasicTextField(
      modifier = Modifier
        .fillMaxSize()
        .onFocusChanged { isFocused = it.isFocused },
      value = fromText,
      onValueChange = onTextChanged,
      cursorBrush = SolidColor(MaterialTheme.colors.primary),
      textStyle = TextStyle(color = MaterialTheme.colors.onSurface)
    )
    // Hint if empty text and box not focused
    if (fromText.isEmpty() && !isFocused) {
      Text(
        modifier = modifier.align(Alignment.BottomEnd),
        text = stringResource(id = R.string.enter_text_to_translate), color = LightBlue
      )
    }
    ProgressButton(
      modifier = Modifier.align(Alignment.BottomEnd),
      text = stringResource(id = R.string.translate),
      isLoading = isTranslating,
      onClick = onTranslateClick
    )
  }
}

@Composable
fun TranslatedTextField(
  modifier: Modifier = Modifier,
  fromText: String,
  toText: String,
  fromUiLanguage: UiLanguage,
  toUiLanguage: UiLanguage,
  onCopyClick: (String) -> Unit,
  onCloseClick: () -> Unit,
  onSpeakerClick: () -> Unit
) {
  Column(modifier = modifier) {
    LanguageIconDisplay(language = fromUiLanguage)
    Spacer(modifier = Modifier.height(14.dp))
    Text(text = fromText, color = MaterialTheme.colors.onSurface)
    Spacer(modifier = Modifier.height(114.dp))
    Row(modifier = Modifier.align(Alignment.End)) {
      IconButton(onClick = { onCopyClick(fromText) }) {
        Icon(
          imageVector = ImageVector.vectorResource(id = R.drawable.copy),
          contentDescription = stringResource(id = R.string.copy)
        )
      }
      Spacer(modifier = Modifier.width(14.dp))
      IconButton(onClick = onCloseClick) {
        Icon(
          imageVector = Icons.Default.Close,
          contentDescription = stringResource(id = R.string.close),
          tint = LightBlue
        )
      }
    }
    Spacer(modifier = Modifier.height(14.dp))
    Divider()
    Spacer(modifier = Modifier.height(14.dp))
    LanguageIconDisplay(language = toUiLanguage)
    Spacer(modifier = Modifier.height(14.dp))
    Text(text = toText, color = MaterialTheme.colors.onSurface)
    Spacer(modifier = Modifier.height(114.dp))
    Row(modifier = Modifier.align(Alignment.End)) {
      IconButton(onClick = { onCopyClick(toText) }) {
        Icon(
          imageVector = ImageVector.vectorResource(id = R.drawable.copy),
          contentDescription = stringResource(id = R.string.copy)
        )
      }
      Spacer(modifier = Modifier.width(14.dp))
      IconButton(onClick = onSpeakerClick) {
        Icon(
          imageVector = ImageVector.vectorResource(id = R.drawable.speaker),
          contentDescription = stringResource(id = R.string.play_loud),
          tint = LightBlue
        )
      }
    }
  }
}