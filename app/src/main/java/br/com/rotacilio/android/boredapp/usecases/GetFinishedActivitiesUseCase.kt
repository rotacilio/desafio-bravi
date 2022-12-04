package br.com.rotacilio.android.boredapp.usecases

import br.com.rotacilio.android.boredapp.db.entities.ActivityEntity
import br.com.rotacilio.android.boredapp.repositories.ActivitiesRepository
import br.com.rotacilio.android.boredapp.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFinishedActivitiesUseCase @Inject constructor(
    private val repository: ActivitiesRepository
) {
    suspend operator fun invoke(): Flow<DataState<List<ActivityEntity>>> = flow {
        emit(DataState.Loading)
        val finishedActivities = repository.getFinishedActivities()
        emit(DataState.Success(data = finishedActivities))
    }.catch { e ->
        e.printStackTrace()
        emit(DataState.Error(e))
    }
}