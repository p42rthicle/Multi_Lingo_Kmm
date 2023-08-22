package me.darthwithap.kmm.multilingo.android

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.darthwithap.kmm.multilingo.android.core.theme.darkColorPalette
import me.darthwithap.kmm.multilingo.android.core.theme.lightColorPalette

@Composable
fun MultiLingoTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colors = if (darkTheme) {
    darkColorPalette
  } else {
    lightColorPalette
  }
  val customFont = FontFamily(
    Font(R.font.sf_pro_regular, FontWeight.Normal),
    Font(R.font.sf_pro_medium, FontWeight.Medium),
    Font(R.font.sf_pro_bold, FontWeight.Bold)
  )
  val typography = Typography(
    h1 = TextStyle(
      fontFamily = customFont,
      fontWeight = FontWeight.Bold,
      fontSize = 30.sp
    ),
    h2 = TextStyle(
      fontFamily = customFont,
      fontWeight = FontWeight.Bold,
      fontSize = 24.sp
    ),
    h3 = TextStyle(
      fontFamily = customFont,
      fontWeight = FontWeight.Medium,
      fontSize = 18.sp
    ),
    body1 = TextStyle(
      fontFamily = customFont,
      fontWeight = FontWeight.Normal,
      fontSize = 14.sp
    ),
    body2 = TextStyle(
      fontFamily = customFont,
      fontWeight = FontWeight.Normal,
      fontSize = 12.sp
    ),
    subtitle1 = TextStyle(
      fontFamily = customFont,
      fontWeight = FontWeight.Light,
      fontSize = 10.sp
    )
  )
  val shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
  )

  MaterialTheme(
    colors = colors,
    typography = typography,
    shapes = shapes,
    content = content
  )
}
