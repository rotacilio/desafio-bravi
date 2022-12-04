package br.com.rotacilio.android.boredapp.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import br.com.rotacilio.android.boredapp.enums.ActivityStatus
import br.com.rotacilio.android.boredapp.extensions.between
import java.util.*

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
    val accessibility: Double,
    val status: ActivityStatus = ActivityStatus.PENDING,
    val start: Date? = null,
    val end: Date? = null,
    val elapsedTime: Long? = null,
) {
    val statusLabel: String
        get() = when (status) {
            ActivityStatus.DONE -> "Concluída"
            ActivityStatus.IN_PROCESS -> "Em realização"
            ActivityStatus.PENDING -> "Pendente"
        }

    val tempoDecorrido: String
        get() = when (status) {
            ActivityStatus.DONE -> "${start?.between(end)} minutos"
            else -> "-"
        }
}
