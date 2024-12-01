package id.handlips.views.profile

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.handlips.data.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) : ViewModel() {
        fun logout(): Boolean =
            try {
                authRepository.logout()
                true
            } catch (e: Exception) {
                false
            }
    fun getCurrentEmail() : String {
        return authRepository.getCurrentUser()?.email.toString()
    }
    }
