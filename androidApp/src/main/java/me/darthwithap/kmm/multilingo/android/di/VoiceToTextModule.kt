package me.darthwithap.kmm.multilingo.android.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import me.darthwithap.kmm.multilingo.android.voice_to_text.data.AndroidVoiceToTextParser
import me.darthwithap.kmm.multilingo.voice_to_text.VoiceToTextParser

@Module
@InstallIn(ViewModelComponent::class)
object VoiceToTextModule {
  @Provides
  @ViewModelScoped
  fun provideVoiceToTextParser(app: Application): VoiceToTextParser {
    return AndroidVoiceToTextParser(app.applicationContext)
  }
}