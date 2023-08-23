package me.darthwithap.kmm.multilingo.android.voice_to_text.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.darthwithap.kmm.multilingo.android.MultiLingoTheme
import me.darthwithap.kmm.multilingo.android.translate.presentation.components.gradientSurface
import kotlin.random.Random

@Composable
fun VoiceRecorderWaveDisplay(
  modifier: Modifier = Modifier,
  powerRatios: List<Float>
) {
  val primaryColor = MaterialTheme.colors.primary

  Box(
    modifier = modifier
      .shadow(
        elevation = 4.dp,
        shape = RoundedCornerShape(18.dp)
      )
      .clip(RoundedCornerShape(18.dp))
      .gradientSurface()
      .padding(16.dp, 8.dp)
      .drawBehind {
        val powerRatioWidth = 3.dp.toPx()
        val powerRatiosCount = (size.width / (2 * powerRatioWidth)).toInt()
        clipRect(
          left = 0f,
          top = 0f,
          bottom = size.height,
          right = size.width
        ) {
          powerRatios
            .takeLast(powerRatiosCount)
            .reversed()
            .forEachIndexed { index, ratio ->
              val yTopStartPoint = center.y - (size.height / 2f * ratio)
              drawRoundRect(
                color = primaryColor,
                topLeft = Offset(size.width - powerRatioWidth * 2f * index, yTopStartPoint),
                size = Size(powerRatioWidth, (center.y - yTopStartPoint) * 2f),
                cornerRadius = CornerRadius(100f)
              )
            }
        }
      }
  ) {

  }
}

@Preview
@Composable
fun VoiceRecorderWaveDisplayPreview() {
  MultiLingoTheme {
    val list = (0..50).map { Random.nextFloat() }
    VoiceRecorderWaveDisplay(
      modifier = Modifier
        .fillMaxWidth()
        .height(100.dp),
      powerRatios = list
    )
  }
}