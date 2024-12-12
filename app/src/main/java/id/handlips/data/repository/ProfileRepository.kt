package id.handlips.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import id.handlips.data.model.FileUploadResponse
import id.handlips.data.model.ProfileResponse
import id.handlips.data.remote.ApiService
import id.handlips.utils.Resource
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
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

    suspend fun updateProfile(
        email: String,
        name: String,
        profilePicture: File? = null
    ): Resource<FileUploadResponse> {
        return try {
            val nameRequestBody = name.toRequestBody("text/plain".toMediaType())
            val profilePicturePart = profilePicture?.asRequestBody("image/*".toMediaTypeOrNull())
            val profilePictureMultipart = profilePicturePart?.let {
                MultipartBody.Part.createFormData(
                    "profile_picture",
                    profilePicture.name,
                    it
                )
            }
            val response =
                apiService.updateProfile(email = email, nameRequestBody, profilePictureMultipart)
            if (response.success) {
                Resource.Success(response)
            } else {
                Resource.Error(response.message)
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred during updating profile")
        }
    }
}
