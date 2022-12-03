package br.com.rotacilio.android.boredapp.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "activities",
    indices = [
        Index("key", name = "idx_activity_key", unique = true)
    ]
)
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val activity: String,
    val type: String,
    val participants: Int,
    val price: Double,
    val link: String? = null,
    val key: String,
    val accessibility: Double
)
