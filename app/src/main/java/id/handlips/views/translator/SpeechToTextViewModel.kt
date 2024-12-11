package id.handlips.views.translator

import androidx.lifecycle.ViewModel
import id.handlips.data.repository.SpeechToTextRepository
import javax.inject.Inject

class SpeechToTextViewModel
    @Inject
    constructor(
        private val speechToTextRepository: SpeechToTextRepository,
    ) : ViewModel() {
        fun speechToText() {}
    }
