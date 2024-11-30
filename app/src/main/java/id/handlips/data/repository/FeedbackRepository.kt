package id.handlips.data.repository

import id.handlips.data.model.FeedbackResponse
import id.handlips.data.remote.ApiService
import id.handlips.utils.Resource
import javax.inject.Inject

class FeedbackRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun sendFeedback(rating: Int, comment: String) : Resource<FeedbackResponse> {
        return try {
            val response = apiService.login(rating, comment)
            if (response.success) {
                Resource.Success(response)
            } else {
                Resource.Error("Gagal mengirim feedback")
                }
            } catch (e: Exception) {
                Resource.Error(e.message ?: "An error occurred during feedback submission")
        }
    }

}