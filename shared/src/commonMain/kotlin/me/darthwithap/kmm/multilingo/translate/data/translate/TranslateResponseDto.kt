package me.darthwithap.kmm.multilingo.translate.data.translate

import kotlinx.serialization.Serializable

@Serializable
data class TranslateResponseDto(
  val translatedText: String
)
