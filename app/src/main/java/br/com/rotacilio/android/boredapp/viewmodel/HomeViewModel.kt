package br.com.rotacilio.android.boredapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rotacilio.android.boredapp.enums.ActivityType
import br.com.rotacilio.android.boredapp.model.Activity
import br.com.rotacilio.android.boredapp.usecases.GetActivityUseCase
import br.com.rotacilio.android.boredapp.utils.DataState
import br.com.rotacilio.android.boredapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getActivityUseCase: GetActivityUseCase
) : ViewModel() {

    sealed interface UiStateVC : UiState {
        data class ActivityWasLoaded(val activity: Activity) : UiStateVC
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState: StateFlow<UiState>
        get() = _uiState.asStateFlow()

    private val _activity = MutableStateFlow<Activity?>(null)
    val activity: StateFlow<Activity?>
        get() = _activity.asStateFlow()

    fun getRandomActivity(type: ActivityType? = null) {
        viewModelScope.launch {
            getActivityUseCase(type).onEach { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        _uiState.emit(UiState.Loading)
                    }
                    is DataState.Success -> {
                        _uiState.emit(UiStateVC.ActivityWasLoaded(dataState.data))
                    }
                    is DataState.Error -> {
                        _uiState.emit(UiState.Error(dataState.statusCode, dataState.exception.message ?: "Unknown error."))
                    }
                }
            }.catch { e ->
                e.printStackTrace()
                _uiState.emit(UiState.Error(-1, e.message ?: "Unknown error."))
            }.launchIn(viewModelScope)
        }
    }
}