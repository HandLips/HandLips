package id.handlips.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import id.handlips.data.model.ProfileResponse
import id.handlips.data.remote.ApiService
import id.handlips.utils.Resource
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun signUp(name: String, email: String): Resource<ProfileResponse> {
        return try {
            val response = apiService.signUp(name, email)
            if (response.success) {
                Resource.Success(response)
            } else {
                Resource.Error(response.message)
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred during signup")
        }
    }
}
