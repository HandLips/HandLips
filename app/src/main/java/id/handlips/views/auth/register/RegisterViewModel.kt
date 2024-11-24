package id.handlips.views.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import id.handlips.data.repository.AuthRepository
import id.handlips.utils.Resource
import id.handlips.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel(){
    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = authRepository.createAccount(email, password)
            _uiState.value = when (result) {
                is Resource.Success -> UiState.Success(result.data)
                is Resource.Error -> UiState.Error(result.message)
            }
        }
    }
}