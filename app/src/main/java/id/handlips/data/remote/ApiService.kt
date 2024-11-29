package id.handlips.data.remote

import id.handlips.data.model.HistoryResponse
import id.handlips.data.model.SoundResponse
import id.handlips.data.model.ProfileResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("history")
    suspend fun getHistory(): HistoryResponse

    @GET("soundboards/{email}")
    suspend fun getSound(
        @Path("email") email: String,
    ): SoundResponse

    @FormUrlEncoded
    @POST("profile")
    suspend fun signUp(
        @Field("name") name: String,
        @Field("email") email: String,
    ): ProfileResponse

    @GET("profile/{email}")
    suspend fun getProfile(
        @Path("email") email: String,
    ): ProfileResponse

}
