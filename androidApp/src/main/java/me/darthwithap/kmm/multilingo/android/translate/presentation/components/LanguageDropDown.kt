package me.darthwithap.kmm.multilingo.android.translate.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.darthwithap.kmm.multilingo.android.R
import me.darthwithap.kmm.multilingo.android.core.theme.LightBlue
import me.darthwithap.kmm.multilingo.core.presentation.UiLanguage

@Composable
fun LanguageDropDown(
  modifier: Modifier = Modifier,
  language: UiLanguage,
  isOpen: Boolean = false,
  onClick: () -> Unit,
  onDismiss: () -> Unit,
  onLanguageSelected: (UiLanguage) -> Unit
) {
  Box(modifier = modifier) {
    DropdownMenu(
      expanded = isOpen,
      onDismissRequest = onDismiss
    ) {
      UiLanguage.allLanguages.forEach { language ->
        LanguageDropDownItem(Modifier.fillMaxWidth(), language) {
          onLanguageSelected(language)
        }
      }
    }
    // anchor ui when the dropdown menu is collapsed / not expanded
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .padding(12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      AsyncImage(
        model = language.drawableRes,
        contentDescription = language.language.languageName,
        modifier = Modifier.size(26.dp)
      )
      Spacer(modifier = Modifier.width(12.dp))
      Text(text = language.language.languageName, color = LightBlue)
      Spacer(modifier = Modifier.width(4.dp))
      Icon(
        imageVector = if (isOpen) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropUp,
        contentDescription = if (isOpen) stringResource(R.string.close)
        else stringResource(R.string.open),
        tint = LightBlue,
        modifier = Modifier.size(26.dp)
      )
    }
  }
}