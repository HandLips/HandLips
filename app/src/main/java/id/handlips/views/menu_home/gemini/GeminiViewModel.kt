package id.handlips.views.menu_home.gemini

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.handlips.data.model.GeminiResponse
import id.handlips.data.model.MessageGemini
import id.handlips.data.model.Topic
import id.handlips.data.repository.GeminiRepository
import id.handlips.utils.Resource
import id.handlips.utils.UiState
import id.handlips.utils.formatDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class GeminiViewModel @Inject constructor(private val repository: GeminiRepository) : ViewModel() {
    private val _messages = MutableStateFlow<List<MessageGemini>>(emptyList())
    val messages: StateFlow<List<MessageGemini>> = _messages

    private val _uiState = MutableStateFlow<UiState<GeminiResponse>>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

   fun geminiResult(topic: Topic) = viewModelScope.launch {
       _uiState.value = UiState.Loading
       val result = repository.generateChatBot(topic)
       _uiState.value = when(result){
           is Resource.Loading -> UiState.Loading
           is Resource.Success -> UiState.Success(result.data)
           is Resource.Error -> UiState.Error(result.message)
       }
   }

    fun addMessage(message: MessageGemini) {
        val currentMessages = _messages.value.toMutableList()
        currentMessages.add(message)
        _messages.value = currentMessages
    }
}
