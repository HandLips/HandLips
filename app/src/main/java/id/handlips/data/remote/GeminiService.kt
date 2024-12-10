package id.handlips.data.remote

import id.handlips.data.model.GeminiResponse
import id.handlips.data.model.Topic
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface GeminiService {

    @POST("generate")
    suspend fun generateEventicle(
        @Body topic: Topic,
    ) : GeminiResponse
}