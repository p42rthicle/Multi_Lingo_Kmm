package me.darthwithap.kmm.multilingo.core.presentation

import me.darthwithap.kmm.multilingo.core.domain.language.Language

actual class UiLanguage(
  actual val language: Language,
  image: String
) {
  actual companion object {
    actual fun byCode(langCode: String): UiLanguage {
      return allLanguages.find { it.language.languageCode == langCode }
        ?: throw IllegalStateException("Invalid or unsupported code")
    }

    actual val allLanguages: List<UiLanguage>
      get() = Language.values().map {
        UiLanguage(it, it.languageName)
      }

  }
}