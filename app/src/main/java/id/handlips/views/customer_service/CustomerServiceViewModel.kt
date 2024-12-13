package id.handlips.views.customer_service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.handlips.data.model.FeedbackResponse
import id.handlips.data.model.ProfileResponse
import id.handlips.data.model.ReportResponse
import id.handlips.data.repository.FeedbackRepository
import id.handlips.utils.Resource
import id.handlips.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerServiceViewModel @Inject constructor(private val csRepository: FeedbackRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow<UiState<FeedbackResponse>>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    private val _uiStateReport = MutableStateFlow<UiState<ReportResponse>>(UiState.Initial)
    val uiStateReport = _uiStateReport.asStateFlow()

    fun sendFeedback(rating: Int, comment: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = csRepository.sendFeedback(rating, comment)
            _uiState.value = when (result) {
                is Resource.Loading -> UiState.Loading
                is Resource.Success -> UiState.Success(result.data)
                is Resource.Error -> UiState.Error(result.message)
            }
        }
    }

    fun sendReport(reason: String) {
        viewModelScope.launch {
            _uiStateReport.value = UiState.Loading
            val result = csRepository.sendReport(reason)

            _uiStateReport.value = when(result){
                is Resource.Loading -> UiState.Loading
                is Resource.Success -> UiState.Success(result.data)
                is Resource.Error -> UiState.Error(result.message)
            }
        }
    }

}