package me.darthwithap.kmm.multilingo.translate.data.translate

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.errors.IOException
import me.darthwithap.kmm.multilingo.core.domain.language.Language
import me.darthwithap.kmm.multilingo.translate.domain.translate.TranslateClient
import me.darthwithap.kmm.multilingo.translate.domain.translate.TranslateError
import me.darthwithap.kmm.multilingo.translate.domain.translate.TranslateException

class KtorTranslateClient(
  private val httpClient: HttpClient
) : TranslateClient {
  override suspend fun translate(
    fromLanguage: Language,
    fromText: String,
    toLanguage: Language
  ): String {

    val response: HttpResponse = try {
      httpClient.post {
        url("https://translate.pl-coding.com/translate")
        contentType(ContentType.Application.Json)
        setBody(
          TranslateDto(
            textToTranslate = fromText,
            sourceLanguageCode = fromLanguage.languageCode,
            targetLanguageCode = toLanguage.languageCode
          )
        )
      }
    } catch (e: IOException) {
      throw TranslateException(TranslateError.ServiceUnavailable)
    }

    when (response.status.value) {
      in 200..299 -> Unit
      500 -> throw TranslateException(TranslateError.ServerError)
      in 400..499 -> throw TranslateException(TranslateError.ClientError)
      else -> throw TranslateException(TranslateError.UnknownError)
    }

    return try {
      response.body<TranslateResponseDto>().translatedText
    } catch (e: Exception) {
      throw TranslateException(TranslateError.ServerError)
    }
  }
}