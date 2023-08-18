package me.darthwithap.kmm.multilingo.translate.data.local

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import me.darthwithap.kmm.multilingo.database.TranslationDatabase

actual class DatabaseDriverFactory {
  actual fun create(): SqlDriver {
    return NativeSqliteDriver(TranslationDatabase.Schema, "translation.db")
  }
}