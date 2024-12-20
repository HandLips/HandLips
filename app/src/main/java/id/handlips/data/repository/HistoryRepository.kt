package id.handlips.data.repository

import android.content.Context
import android.provider.Settings.Global.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import dagger.hilt.android.qualifiers.ApplicationContext
import id.handlips.R
import id.handlips.data.model.HistoryResponse
import id.handlips.data.remote.ApiService
import id.handlips.utils.Resource
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val apiService: ApiService,
    @ApplicationContext private val context: Context
) {
    fun getHistory(email: String): LiveData<Resource<HistoryResponse>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.getHistory(email)
            if (response.data.isNotEmpty()) {
                emit(Resource.Success(response))
            } else {
                emit(Resource.Error(context.getString(R.string.failed_to_fetch_data)))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: context.getString(R.string.an_error_occurred)))
        }
    }

    fun getLatestHistory(email: String): LiveData<Resource<HistoryResponse>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.getHistory(email)
            if (response.data.isNotEmpty()) {
                // Urutkan berdasarkan created_at dan ambil 5 data terbaru
                val latestData = response.data.map { historyItem ->
                    historyItem.copy(
                        message = historyItem.message.sortedByDescending { it?.createdAt }.take(5)
                    )
                }
                val updatedResponse = response.copy(data = latestData)
                emit(Resource.Success(updatedResponse))
            } else {
                emit(Resource.Error(context.getString(R.string.failed_to_fetch_data)))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: context.getString(R.string.an_error_occurred)))
        }
    }

}