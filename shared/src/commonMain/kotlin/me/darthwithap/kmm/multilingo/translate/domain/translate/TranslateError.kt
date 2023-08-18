package me.darthwithap.kmm.multilingo.translate.domain.translate

enum class TranslateError {
  ServiceUnavailable,
  ClientError,
  ServerError,
  UnknownError
}

class TranslateException(val error: TranslateError) :
  Exception("Invalid or unsupported language. Error: $error")
