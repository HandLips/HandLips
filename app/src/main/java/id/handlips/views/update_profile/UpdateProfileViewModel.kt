package id.handlips.views.update_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import id.handlips.data.model.FileUploadResponse
import id.handlips.data.model.ProfileResponse
import id.handlips.data.repository.AuthRepository
import id.handlips.data.repository.ProfileRepository
import id.handlips.utils.Resource
import id.handlips.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val repository: ProfileRepository,
    ) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<FileUploadResponse>>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    fun updateProfile(email: String, name: String, profilePicture: File) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = repository.updateProfile(email, name, profilePicture)
            _uiState.value = when (result) {
                is Resource.Loading -> UiState.Loading
                is Resource.Success -> UiState.Success(result.data)
                is Resource.Error -> UiState.Error(result.message)
            }
        }
    }
    fun getCurrent(): FirebaseUser? {
        return authRepository.getCurrentUser()
    }

    fun getProfile(email: String) = repository.getProfile(email)

}