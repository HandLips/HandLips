package id.handlips.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import dagger.hilt.android.qualifiers.ApplicationContext
import id.handlips.R
import id.handlips.data.model.SoundResponse
import id.handlips.data.remote.ApiService
import id.handlips.utils.Resource
import javax.inject.Inject

class ShortcutRepository @Inject constructor(
    private val apiService: ApiService,
    @ApplicationContext private val context: Context
) {
    fun getSound(): LiveData<Resource<SoundResponse>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.getSound()
            if (response.success) {
                emit(Resource.Success(response))
            } else {
                emit(Resource.Error(context.getString(R.string.failed_to_fetch_data)))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: context.getString(R.string.an_error_occurred)))
        }
    }
}