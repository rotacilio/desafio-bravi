package br.com.rotacilio.android.boredapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rotacilio.android.boredapp.db.entities.ActivityEntity
import br.com.rotacilio.android.boredapp.usecases.GetFinishedActivitiesUseCase
import br.com.rotacilio.android.boredapp.utils.DataState
import br.com.rotacilio.android.boredapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinishedActivitiesViewModel @Inject constructor(
    private val getFinishedActivitiesUseCase: GetFinishedActivitiesUseCase
) : ViewModel() {

    sealed interface UiStateVC : UiState {
        data class FinishedActivitiesWasLoaded(val data: List<ActivityEntity>) : UiStateVC
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState: StateFlow<UiState>
        get() = _uiState.asStateFlow()

    private fun getFinishedActivities() {
        viewModelScope.launch {
            getFinishedActivitiesUseCase().onEach { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        _uiState.emit(UiState.Loading)
                    }
                    is DataState.Success -> {
                        _uiState.emit(UiStateVC.FinishedActivitiesWasLoaded(data = dataState.data))
                    }
                    else -> Unit
                }
            }.catch { e ->
                e.printStackTrace()
            }.launchIn(viewModelScope)
        }
    }

    init {
        getFinishedActivities()
    }
}