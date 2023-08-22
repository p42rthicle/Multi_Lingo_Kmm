package me.darthwithap.kmm.multilingo.android.translate.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.darthwithap.kmm.multilingo.android.core.theme.LightBlue
import me.darthwithap.kmm.multilingo.core.presentation.UiLanguage

@Composable
fun LanguageIconDisplay(
  modifier: Modifier = Modifier,
  language: UiLanguage
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    SmallLanguageIcon(modifier = modifier, language = language)
    Spacer(modifier = Modifier.width(8.dp))
    Text(text = language.language.languageName, color = LightBlue)
  }
}
