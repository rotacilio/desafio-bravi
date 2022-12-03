package br.com.rotacilio.android.boredapp.remote

import br.com.rotacilio.android.boredapp.model.Activity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ActivitiesService {

    @GET("activity")
    suspend fun getActivity(
        @Query("type") type: String?
    ): Response<Activity>
}