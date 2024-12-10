package id.handlips.data.remote

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface GeminiService {

    @FormUrlEncoded
    @POST("generate")
    suspend fun generateEventicle(
        @Field("topic") topic: String,
    )
}