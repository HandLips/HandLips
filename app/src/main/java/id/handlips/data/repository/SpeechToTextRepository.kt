package id.handlips.data.repository

import id.handlips.data.remote.SpeechToTextApiService
import id.handlips.di.SpeechToTextRetrofit
import javax.inject.Inject

class SpeechToTextRepository
    @Inject
    constructor(
        @SpeechToTextRetrofit private val apiService: SpeechToTextApiService,
    ) {
        suspend fun speechToText() {}
    }
