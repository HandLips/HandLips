package id.handlips.views.home

import androidx.lifecycle.ViewModel
import com.google.android.play.core.integrity.au
import dagger.hilt.android.lifecycle.HiltViewModel
import id.handlips.data.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    fun getCurrentUser() {
        authRepository.getCurrentUser()
    }

    fun isLoggin(): Boolean {
        return authRepository.isUserAuthenticated()
    }
}