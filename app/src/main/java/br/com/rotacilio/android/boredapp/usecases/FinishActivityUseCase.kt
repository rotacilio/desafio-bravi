package br.com.rotacilio.android.boredapp.usecases

import br.com.rotacilio.android.boredapp.db.entities.ActivityEntity
import br.com.rotacilio.android.boredapp.enums.ActivityStatus
import br.com.rotacilio.android.boredapp.repositories.ActivitiesRepository
import br.com.rotacilio.android.boredapp.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject

class FinishActivityUseCase @Inject constructor(
    private val repository: ActivitiesRepository
) {
    suspend operator fun invoke(activityEntity: ActivityEntity): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        val activity = activityEntity.copy(
            status = ActivityStatus.DONE,
            end = Date()
        )
        val result = repository.createActivity(activity)
        emit(DataState.Success(data = result > 0))
    }.catch { e ->
        e.printStackTrace()
        emit(DataState.Error(e))
    }
}