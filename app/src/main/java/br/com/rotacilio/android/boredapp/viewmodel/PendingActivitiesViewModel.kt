package br.com.rotacilio.android.boredapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rotacilio.android.boredapp.db.entities.ActivityEntity
import br.com.rotacilio.android.boredapp.usecases.FinishActivityUseCase
import br.com.rotacilio.android.boredapp.usecases.GetPendingActivitiesUseCase
import br.com.rotacilio.android.boredapp.usecases.StartActivityUseCase
import br.com.rotacilio.android.boredapp.utils.DataState
import br.com.rotacilio.android.boredapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PendingActivitiesViewModel @Inject constructor(
    private val getPendingActivitiesUseCase: GetPendingActivitiesUseCase,
    private val startActivityUseCase: StartActivityUseCase,
    private val finishActivityUseCase: FinishActivityUseCase,
) : ViewModel() {

    sealed interface UiStateVC : UiState {
        data class PendingActivitiesWasLoaded(val data: List<ActivityEntity>) : UiStateVC
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState: StateFlow<UiState>
        get() = _uiState.asStateFlow()

    private fun getPendingActivities() {
        viewModelScope.launch {
            getPendingActivitiesUseCase().onEach { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        _uiState.emit(UiState.Loading)
                    }
                    is DataState.Success -> {
                        _uiState.emit(UiStateVC.PendingActivitiesWasLoaded(data = dataState.data))
                    }
                    else -> Unit
                }
            }.catch { e ->
                e.printStackTrace()
            }.launchIn(viewModelScope)
        }
    }

    fun startActivity(activityEntity: ActivityEntity) {
        viewModelScope.launch {
            startActivityUseCase(activityEntity).onEach { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        if (dataState.data) {
                            getPendingActivities()
                        }
                    }
                    else -> Unit
                }
            }.catch { e ->
                e.printStackTrace()
            }.launchIn(viewModelScope)
        }
    }

    fun finishActivity(activityEntity: ActivityEntity) {
        viewModelScope.launch {
            finishActivityUseCase(activityEntity).onEach { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        if (dataState.data) {
                            getPendingActivities()
                        }
                    }
                    else -> Unit
                }
            }.catch { e ->
                e.printStackTrace()
            }.launchIn(viewModelScope)
        }
    }

    init {
        getPendingActivities()
    }
}