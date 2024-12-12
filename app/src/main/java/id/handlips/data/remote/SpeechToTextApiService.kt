package id.handlips.data.remote

import id.handlips.data.model.ListSpeechToTextResponse
import id.handlips.data.model.SpeechToTextResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface SpeechToTextApiService {
    @GET("speechtotext/{email}")
    suspend fun getSpeechText(
        @Path("email") email: String,
    ): ListSpeechToTextResponse

    @Multipart
    @POST("speechtotext/")
    suspend fun speechToText(
        @Part("title") title: RequestBody,
        @Part("email") email: RequestBody,
        @Part audio: MultipartBody.Part,
    ): SpeechToTextResponse
}
