package me.darthwithap.kmm.multilingo.translate.data.local

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import me.darthwithap.kmm.multilingo.database.TranslationDatabase

actual class DatabaseDriverFactory(
  private val context: Context
) {
  actual fun create(): SqlDriver {
    return AndroidSqliteDriver(TranslationDatabase.Schema, context, "translation.db")
  }
}