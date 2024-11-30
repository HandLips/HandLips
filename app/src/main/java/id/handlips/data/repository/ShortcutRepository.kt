package id.handlips.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import dagger.hilt.android.qualifiers.ApplicationContext
import id.handlips.R
import id.handlips.data.model.ListSoundResponse
import id.handlips.data.model.SoundResponse
import id.handlips.data.remote.ApiService
import id.handlips.utils.Resource
import id.handlips.views.shortcut.ShortcutViewModel
import javax.inject.Inject

class ShortcutRepository @Inject constructor(
    private val apiService: ApiService,
    @ApplicationContext private val context: Context,
) {
    fun getSound(email: String): LiveData<Resource<ListSoundResponse>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.getSound(email)
            if (response.success) {
                emit(Resource.Success(response))
            } else {
                emit(Resource.Error(context.getString(R.string.failed_to_fetch_data)))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: context.getString(R.string.an_error_occurred)))
        }
    }

    suspend fun createSound(title: String, text: String, email: String): Resource<SoundResponse> {
        return try {
            val response = apiService.createSound(title, text, email)
            if (response.success) {
                Resource.Success(response)
            } else {
                Resource.Error(response.message)
                }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred during sound creation")
        }
    }
}