package id.handlips.data.remote

import id.handlips.data.model.HistoryResponse
<<<<<<< HEAD
import id.handlips.data.model.SoundResponse
=======
import id.handlips.data.model.ProfileResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
>>>>>>> e3fca7a311f4a79345ba8a44bd09abc403e0dad4
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("history")
    suspend fun getHistory(): HistoryResponse

<<<<<<< HEAD
    @GET("soundboards")
    suspend fun getSound(): SoundResponse

}
=======
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
>>>>>>> e3fca7a311f4a79345ba8a44bd09abc403e0dad4
