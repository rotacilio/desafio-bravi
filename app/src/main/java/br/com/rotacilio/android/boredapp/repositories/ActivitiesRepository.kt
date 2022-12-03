package br.com.rotacilio.android.boredapp.repositories

import br.com.rotacilio.android.boredapp.remote.ActivitiesService
import javax.inject.Inject

class ActivitiesRepository @Inject constructor(
    private val service: ActivitiesService
) {
    suspend fun getActivity(type: String?) =
        service.getActivity(type)
}