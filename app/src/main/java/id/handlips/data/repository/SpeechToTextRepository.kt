package id.handlips.data.repository

import id.handlips.data.remote.SpeechToTextApiService
import javax.inject.Inject

class SpeechToTextRepository
    @Inject
    constructor(
        private val apiService: SpeechToTextApiService,
    ) {
        suspend fun speechToText() {}
    }
