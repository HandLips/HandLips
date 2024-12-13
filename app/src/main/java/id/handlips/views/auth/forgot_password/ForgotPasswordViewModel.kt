package id.handlips.views.auth.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import id.handlips.data.repository.AuthRepository
import id.handlips.utils.Resource
import id.handlips.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    fun forgotPassword(email: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = authRepository.sendPasswordReset(email)
            _uiState.value = when (result) {
                is Resource.Loading -> UiState.Loading
                is Resource.Success -> UiState.Success(Unit) // Tidak ada data spesifik selain sukses
                is Resource.Error -> UiState.Error(result.message)
            }
        }
    }


}