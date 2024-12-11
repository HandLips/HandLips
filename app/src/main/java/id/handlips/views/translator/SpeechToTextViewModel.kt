package id.handlips.views.translator

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.handlips.data.repository.SpeechToTextRepository
import id.handlips.di.SpeechToTextRetrofit
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class SpeechToTextViewModel
    @Inject
    constructor(
        @SpeechToTextRetrofit private val speechToTextRepository: SpeechToTextRepository,
    ) : ViewModel() {
        private val _resultText = mutableStateOf("")
        val resultText: State<String> get() = _resultText

        private val _isLoading = mutableStateOf(false)
        val isLoading: State<Boolean> get() = _isLoading

        fun speechToText(
            title: String,
            email: String = "wahhab@gmail.com",
            audioFile: File,
        ) {
            viewModelScope.launch {
                _isLoading.value = true
                try {
                    val response = speechToTextRepository.speechToText(title, email, audioFile)
                    _resultText.value = response.data.text
                } catch (e: Exception) {
                    _resultText.value = "Error: ${e.localizedMessage}"
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }
