package id.handlips.views.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import id.handlips.data.model.ProfileResponse
import id.handlips.data.repository.AuthRepository
import id.handlips.data.repository.ProfileRepository
import id.handlips.utils.Resource
import id.handlips.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<ProfileResponse>>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    fun signUp(email: String, password: String, name: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            // Langkah 1: Buat akun di Firebase
            val resultFirebase = authRepository.createAccount(email, password)
            if (resultFirebase is Resource.Error) {
                _uiState.value = UiState.Error(resultFirebase.message)
                return@launch
            }

            val resultApi = profileRepository.signUp(name, email)
            _uiState.value = when (resultApi) {
                is Resource.Loading -> UiState.Loading
                is Resource.Success -> UiState.Success(resultApi.data)
                is Resource.Error -> UiState.Error(resultApi.message)
            }
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return authRepository.getCurrentUser()
    }

}