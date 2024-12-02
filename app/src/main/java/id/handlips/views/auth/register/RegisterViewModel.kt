package id.handlips.views.auth.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import id.handlips.data.model.ProfileResponse
import id.handlips.data.repository.AuthRepository
import id.handlips.data.repository.ProfileRepository
import id.handlips.utils.GoogleSignInState
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

    private val _googleState = mutableStateOf(GoogleSignInState())
    val googleState: State<GoogleSignInState> = _googleState

    fun googleSignIn(credential: AuthCredential) = viewModelScope.launch {
        authRepository.signInWithGoogle(credential).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _googleState.value = GoogleSignInState(success = result.data)
                }
                is Resource.Loading -> {
                    _googleState.value = GoogleSignInState(loading = true)
                }
                is Resource.Error -> {
                    _googleState.value = GoogleSignInState(error = result.message!!)
                }
            }
        }
    }
    fun signUp(email: String, password: String, name: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

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

}