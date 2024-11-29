package id.handlips.views.home

import androidx.lifecycle.ViewModel
import com.google.android.play.core.integrity.au
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import id.handlips.data.repository.AuthRepository
import id.handlips.data.repository.HistoryRepository
import id.handlips.data.repository.ProfileRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val authRepository: AuthRepository, private val historyRepository: HistoryRepository, private val profileRepository: ProfileRepository) : ViewModel() {
    fun getCurrentEmail() : String {
        return authRepository.getCurrentUser()?.email.toString()
    }

    fun getProfile(email: String) = profileRepository.getProfile(email)

    fun isLoggin(): Boolean {
        return authRepository.isUserAuthenticated()
    }

    fun getHistory() = historyRepository.getHistory()
}