package br.com.rotacilio.android.boredapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import br.com.rotacilio.android.boredapp.db.entities.ActivityEntity

@Dao
interface ActivityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(activityEntity: ActivityEntity): Long

}