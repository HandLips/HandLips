package id.handlips.views.translator

import androidx.lifecycle.ViewModel
import id.handlips.data.repository.SpeechToTextRepository
import id.handlips.di.SpeechToTextRetrofit
import javax.inject.Inject

class SpeechToTextViewModel
    @Inject
    constructor(
        @SpeechToTextRetrofit private val speechToTextRepository: SpeechToTextRepository,
    ) : ViewModel() {
        fun speechToText() {}
    }
