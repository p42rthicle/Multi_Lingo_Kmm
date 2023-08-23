package me.darthwithap.kmm.multilingo.android.voice_to_text.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import me.darthwithap.kmm.multilingo.voice_to_text.VoiceToTextParser
import me.darthwithap.kmm.multilingo.voice_to_text.presentation.VoiceToTextEvent
import me.darthwithap.kmm.multilingo.voice_to_text.presentation.VoiceToTextViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidVoiceToTextViewModel @Inject constructor(
  private val parser: VoiceToTextParser
) : ViewModel() {
  private val viewModel by lazy { VoiceToTextViewModel(parser, viewModelScope) }
  val state = viewModel.state

  fun onEvent(event: VoiceToTextEvent) {
    viewModel.onEvent(event)
  }
}