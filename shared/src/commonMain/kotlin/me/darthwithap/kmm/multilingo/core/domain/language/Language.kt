package me.darthwithap.kmm.multilingo.core.domain.language

enum class Language(val languageCode: String, val languageName: String) {
  ENGLISH("en", "English"),
  ARABIC("ar", "Arabic"),
  AZERBAIJANI("az", "Azerbaijani"),
  CHINESE("zh", "Chinese"),
  CZECH("cs", "Czech"),
  DANISH("da", "Danish"),
  DUTCH("nl", "Dutch"),
  FINNISH("fi", "Finnish"),
  FRENCH("fr", "French"),
  GERMAN("de", "German"),
  GREEK("el", "Greek"),
  HEBREW("he", "Hebrew"),
  HINDI("hi", "Hindi"),
  HUNGARIAN("hu", "Hungarian"),
  INDONESIAN("id", "Indonesian"),
  IRISH("ga", "Irish"),
  ITALIAN("it", "Italian"),
  JAPANESE("ja", "Japanese"),
  KOREAN("ko", "Korean"),
  PERSIAN("fa", "Persian"),
  POLISH("pl", "Polish"),
  PORTUGUESE("pt", "Portuguese"),
  RUSSIAN("ru", "Russian"),
  SLOVAK("sk", "Slovak"),
  SPANISH("es", "Spanish"),
  SWEDISH("sv", "Swedish"),
  TURKISH("tr", "Turkish"),
  UKRAINIAN("uk", "Ukrainian");

  companion object {
    fun byCode(code: String): Language {
      return values().find { it.languageCode == code }
        ?: throw IllegalArgumentException("Invalid or unsupported language code")
    }
  }
}