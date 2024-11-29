package id.handlips.views.on_boarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.handlips.data.local.DataStorePreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class OnBoardingViewModel @Inject constructor(
    private val dataStore: DataStorePreference
) : ViewModel() {

    fun saveOnBoardingState(completed: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveOnBoardingState(completed = completed)
        }
    }
}