package br.com.rotacilio.android.boredapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.rotacilio.android.boredapp.db.dao.ActivityDao
import br.com.rotacilio.android.boredapp.db.entities.ActivityEntity

@Database(
    entities = [
        ActivityEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao
}