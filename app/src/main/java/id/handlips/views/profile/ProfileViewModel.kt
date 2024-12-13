package id.handlips.views.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import id.handlips.data.repository.AuthRepository
import id.handlips.data.repository.ProfileRepository
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {
    fun logout(): Boolean =
        try {
            authRepository.logout()
            true
        } catch (e: Exception) {
            false
        }

    fun getCurrent(): FirebaseUser? {
        return authRepository.getCurrentUser()
    }

    fun getProfile(email: String) = profileRepository.getProfile(email)

}
