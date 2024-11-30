package id.handlips.views.shortcut

import android.content.Context
import android.media.JetPlayer
import android.media.MediaPlayer
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.play.core.integrity.au
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import id.handlips.R
import id.handlips.data.model.ProfileResponse
import id.handlips.data.model.SoundResponse
import id.handlips.data.remote.ApiService
import id.handlips.data.repository.AuthRepository
import id.handlips.data.repository.ShortcutRepository
import id.handlips.utils.Resource
import id.handlips.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShortcutViewModel @Inject constructor(
    private val shortcutRepository: ShortcutRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<SoundResponse>>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    fun getEmail() = authRepository.getCurrentUser()?.email.toString()
    fun getSound(email: String) = shortcutRepository.getSound(email)

    fun createSound(title: String, text: String) {
        viewModelScope.launch {
            val respone = shortcutRepository.createSound(title, text, getEmail())
            _uiState.value = when(respone){
                is Resource.Loading -> UiState.Loading
                is Resource.Success -> UiState.Success(respone.data)
                is Resource.Error -> UiState.Error(respone.message)
            }
        }
    }
}