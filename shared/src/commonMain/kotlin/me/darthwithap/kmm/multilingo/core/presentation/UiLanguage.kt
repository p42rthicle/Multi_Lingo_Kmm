package me.darthwithap.kmm.multilingo.core.presentation

import me.darthwithap.kmm.multilingo.core.domain.language.Language

expect class UiLanguage {
  val language: Language

  companion object {
    fun byCode(langCode: String): UiLanguage
    val allLanguages: List<UiLanguage>
  }
}