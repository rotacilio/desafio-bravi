package br.com.rotacilio.android.boredapp.repositories

import br.com.rotacilio.android.boredapp.db.dao.ActivityDao
import br.com.rotacilio.android.boredapp.db.entities.ActivityEntity
import br.com.rotacilio.android.boredapp.remote.ActivitiesService
import javax.inject.Inject

class ActivitiesRepository @Inject constructor(
    private val service: ActivitiesService,
    private val dao: ActivityDao
) {
    suspend fun getActivity(type: String?) =
        service.getActivity(type)

    suspend fun createActivity(entity: ActivityEntity) = dao.create(entity)

    suspend fun getPendingActivities() = dao.getPendingActivities()
}