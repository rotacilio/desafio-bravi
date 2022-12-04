package br.com.rotacilio.android.boredapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.rotacilio.android.boredapp.db.entities.ActivityEntity

@Dao
interface ActivityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(activityEntity: ActivityEntity): Long

    @Query("SELECT * FROM activities WHERE status IN (0, 2)")
    suspend fun getPendingActivities(): List<ActivityEntity>
}