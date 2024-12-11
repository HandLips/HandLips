package id.handlips.data.repository

import id.handlips.data.model.SpeechToTextResponse
import id.handlips.data.remote.SpeechToTextApiService
import id.handlips.di.SpeechToTextRetrofit
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class SpeechToTextRepository
    @Inject
    constructor(
        @SpeechToTextRetrofit private val apiService: SpeechToTextApiService,
    ) {
        suspend fun speechToText(
            title: String,
            email: String,
            audioFile: File,
        ): SpeechToTextResponse {
            val titlePart = title.toRequestBody("text/plain".toMediaTypeOrNull())
            val emailPart = email.toRequestBody("text/plain".toMediaTypeOrNull())
            val audioPart =
                MultipartBody.Part.createFormData(
                    "audio",
                    audioFile.name,
                    audioFile.asRequestBody("audio/wav".toMediaTypeOrNull()),
                )

            return apiService.speechToText(
                title = titlePart,
                email = emailPart,
                audio = audioPart,
            )
        }
    }
