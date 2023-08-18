package me.darthwithap.kmm.multilingo.translate.data.local

import com.squareup.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
  fun create(): SqlDriver
}