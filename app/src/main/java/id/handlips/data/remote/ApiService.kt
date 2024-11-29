package id.handlips.data.remote

import id.handlips.data.model.HistoryResponse
import id.handlips.data.model.ProfileResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("history")
    suspend fun getHistory(): HistoryResponse

    @FormUrlEncoded
    @POST("profile")
    suspend fun signUp(
        @Field("name") name: String,
        @Field("email") email: String,
    ): ProfileResponse

    @FormUrlEncoded
    @POST("profile")
    suspend fun getProfile(
        @Field("email") email: String,
    ): ProfileResponse


}
