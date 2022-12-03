package br.com.rotacilio.android.boredapp.usecases

import br.com.rotacilio.android.boredapp.enums.ActivityType
import br.com.rotacilio.android.boredapp.model.Activity
import br.com.rotacilio.android.boredapp.repositories.ActivitiesRepository
import br.com.rotacilio.android.boredapp.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetActivityUseCase @Inject constructor(
    private val repository: ActivitiesRepository
) {
    suspend operator fun invoke(type: ActivityType?): Flow<DataState<Activity>> = flow {
        emit(DataState.Loading)
        val typeStr = type?.name?.lowercase()
        val response = repository.getActivity(typeStr)
        if (response.isSuccessful && response.body() != null)
            emit(DataState.Success(data = response.body()!!))
        else
            emit(DataState.Error(HttpException(response)))
    }.catch { e ->
        e.printStackTrace()
        emit(DataState.Error(e))
    }
}