package me.darthwithap.kmm.multilingo.android.translate.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import me.darthwithap.kmm.multilingo.android.core.theme.LightBlue
import me.darthwithap.kmm.multilingo.translate.presentation.UiTranslationHistoryItem
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TranslationHistoryItem(
  modifier: Modifier = Modifier,
  historyItem: UiTranslationHistoryItem,
  onClick: () -> Unit
) {
  Column(
    modifier = modifier
      .shadow(
        elevation = 4.dp,
        shape = RoundedCornerShape(18.dp)
      )
      .clip(RoundedCornerShape(18.dp))
      .gradientSurface()
      .clickable { onClick() }
      .padding(14.dp)
  ) {
    Row(
      horizontalArrangement = Arrangement.End,
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp, 2.dp)
    ) {
      Text(
        text = historyItem.timestamp.toFormattedString(),
        style = MaterialTheme.typography.subtitle1,
        color = LightBlue
      )
    }
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
      SmallLanguageIcon(language = historyItem.fromUiLanguage)
      Spacer(modifier = Modifier.width(16.dp))
      Text(
        text = historyItem.fromText,
        color = LightBlue,
        style = MaterialTheme.typography.body2
      )
    }
    Spacer(modifier = Modifier.height(22.dp))
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
      SmallLanguageIcon(language = historyItem.toUiLanguage)
      Spacer(modifier = Modifier.width(16.dp))
      Text(
        text = historyItem.toText,
        color = MaterialTheme.colors.onSurface,
        style = MaterialTheme.typography.body1,
        fontWeight = FontWeight.Medium
      )
    }
  }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun Long.toFormattedString(): String {
  val formatter = DateTimeFormatter.ofPattern("dd MMM yy, HH:mm a", Locale.ENGLISH)
  val dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
  return dateTime.format(formatter)
}
