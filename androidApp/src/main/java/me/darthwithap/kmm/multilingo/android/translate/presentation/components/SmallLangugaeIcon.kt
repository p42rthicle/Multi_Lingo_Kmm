package me.darthwithap.kmm.multilingo.android.translate.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.darthwithap.kmm.multilingo.core.presentation.UiLanguage

@Composable
fun SmallLanguageIcon(
  modifier: Modifier = Modifier,
  language: UiLanguage
) {
  AsyncImage(
    modifier = modifier.size(24.dp),
    model = language.drawableRes,
    contentDescription = language.language.languageName
  )
}