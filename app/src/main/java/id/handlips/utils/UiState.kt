package id.handlips.utils

import com.google.firebase.auth.FirebaseUser

sealed class UiState {
    object Initial : UiState()
    object Loading : UiState()
    data class Success(val user: FirebaseUser) : UiState()
    data class Error(val message: String) : UiState()
}