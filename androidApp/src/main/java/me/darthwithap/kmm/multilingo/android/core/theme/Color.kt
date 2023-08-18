package me.darthwithap.kmm.multilingo.android.core.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color
import me.darthwithap.kmm.multilingo.core.presentation.SharedColors

val VioletAccent = Color(SharedColors.VioletAccent)
val DarkGrey = Color(SharedColors.DarkGrey)
val LightBlue = Color(SharedColors.LightBlue)
val LightBlueGrey = Color(SharedColors.LightBlueGrey)
val TextBlackOnWhite = Color(SharedColors.TextBlackOnWhite)

val lightColorPalette = lightColors(
  primary = VioletAccent,
  background = LightBlueGrey,
  onPrimary = Color.White,
  onBackground = TextBlackOnWhite,
  surface = Color.White,
  onSurface = TextBlackOnWhite
)

val darkColorPalette = darkColors(
  primary = VioletAccent,
  background = DarkGrey,
  onPrimary = Color.White,
  onBackground = Color.White,
  surface = DarkGrey,
  onSurface = Color.White
)