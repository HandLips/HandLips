package id.handlips.data.repository

import id.handlips.data.model.FeedbackResponse
import id.handlips.data.model.ReportResponse
import id.handlips.data.remote.ApiService
import id.handlips.utils.Resource
import javax.inject.Inject

class FeedbackRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun sendFeedback(rating: Int, comment: String): Resource<FeedbackResponse> {
        return try {
            val response = apiService.createFeedback(rating, comment)
            if (response.success) {
                Resource.Success(response)
            } else {
                Resource.Error("Gagal mengirim feedback")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred during feedback submission")
        }
    }

    suspend fun sendReport(reason: String): Resource<ReportResponse> {
        return try {
            val response = apiService.createReport(reason)
            if (response.success) {
                Resource.Success(response)
            } else {
                Resource.Error("Gagal mengirim laporan")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred during report submission")
        }
    }

}