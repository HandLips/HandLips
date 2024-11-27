package id.handlips.data.remote

import id.handlips.data.model.HistoryResponse
import retrofit2.http.GET

interface ApiService {
    @GET("history")
    suspend fun getHistory(): HistoryResponse
}