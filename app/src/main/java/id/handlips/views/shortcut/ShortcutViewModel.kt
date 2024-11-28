package id.handlips.views.shortcut

import android.content.Context
import android.media.JetPlayer
import android.media.MediaPlayer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import id.handlips.R
import id.handlips.data.remote.ApiService
import id.handlips.data.repository.ShortcutRepository
import javax.inject.Inject

@HiltViewModel
class ShortcutViewModel@Inject constructor(private val shortcutRepository: ShortcutRepository, saveState : SavedStateHandle) : ViewModel() {

    fun getSound() = shortcutRepository.getSound()
}