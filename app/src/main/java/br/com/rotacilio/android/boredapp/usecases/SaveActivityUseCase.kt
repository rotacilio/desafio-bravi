package br.com.rotacilio.android.boredapp.usecases

import br.com.rotacilio.android.boredapp.db.entities.ActivityEntity
import br.com.rotacilio.android.boredapp.model.Activity
import br.com.rotacilio.android.boredapp.repositories.ActivitiesRepository
import br.com.rotacilio.android.boredapp.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveActivityUseCase @Inject constructor(
    private val repository: ActivitiesRepository
) {
    suspend operator fun invoke(activity: Activity): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)

        val entity = ActivityEntity(
            activity = activity.activity,
            type = activity.type,
            participants = activity.participants,
            price = activity.price,
            link = activity.link.ifEmpty { null },
            key = activity.key,
            accessibility = activity.accessibility
        )

        val activityId = repository.createActivity(entity)
        emit(DataState.Success(data = activityId > 0))
    }.catch { e ->
        e.printStackTrace()
        emit(DataState.Error(e))
    }
}