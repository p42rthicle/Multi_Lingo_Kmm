package me.darthwithap.kmm.multilingo.android.translate.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp
import me.darthwithap.kmm.multilingo.core.presentation.UiLanguage

@Composable
fun LanguageDropDownItem(
  modifier: Modifier = Modifier,
  language: UiLanguage,
  onClick: () -> Unit
) {
  DropdownMenuItem(onClick = onClick, modifier = modifier) {
    Image(
      painter = painterResource(id = language.drawableRes),
      contentDescription = language.language.languageName,
      modifier = Modifier.size(34.dp)
    )
    Spacer(modifier = Modifier.width(12.dp))
    Text(
      text = language.language.languageName.lowercase()
        .capitalize(androidx.compose.ui.text.intl.Locale.current)
    )
  }
}