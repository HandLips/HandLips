package id.handlips.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import id.handlips.data.model.ProfileResponse
import id.handlips.data.remote.ApiService
import id.handlips.utils.Resource
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


class ProfileRepository @Inject constructor(private val apiService: ApiService)  {

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

    fun getProfile(email: String): LiveData<Resource<ProfileResponse>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.getProfile(email)
            if (response.success) {
                emit(Resource.Success(response))
            } else {
                emit(Resource.Error(response.message))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred during fetching profile"))
        }
    }

    fun updateProfile(name: String, profilePicture: File): LiveData<Resource<ProfileResponse>> = liveData {
        emit(Resource.Loading)
        try {
            val requestBody = name.toRequestBody("text/plain".toMediaType())
            val profilePicturePart = profilePicture.asRequestBody("image/*".toMediaType())
            val profilePictureMultipart = MultipartBody.Part.createFormData("profile_picture", profilePicture.name, profilePicturePart)
            val response = apiService.updateProfile(requestBody, profilePictureMultipart)
            if (response.success) {
                emit(Resource.Success(response))
            } else {
                emit(Resource.Error(response.message))
                }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred during updating profile"))

        }
    }
}
