package id.handlips.data.remote

import id.handlips.data.model.HistoryResponse
import id.handlips.data.model.SoundResponse
import retrofit2.http.GET

interface ApiService {
    @GET("history")
    suspend fun getHistory(): HistoryResponse

    @GET("soundboards")
    suspend fun getSound(): SoundResponse

}