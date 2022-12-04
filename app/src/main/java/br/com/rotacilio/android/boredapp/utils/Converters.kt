package br.com.rotacilio.android.boredapp.utils

import androidx.room.TypeConverter
import br.com.rotacilio.android.boredapp.enums.ActivityStatus
import java.text.SimpleDateFormat
import java.util.*

class Converters {

    @TypeConverter
    fun fromIntToActivityStatus(status: Int): ActivityStatus {
        return ActivityStatus.values()[status]
    }

    @TypeConverter
    fun fromActivityStatusToInt(status: ActivityStatus): Int {
        return ActivityStatus.values().indexOf(status)
    }

    @TypeConverter
    fun fromStringToDate(start: String?): Date? {
        start ?: return null
        return SimpleDateFormat(Constants.DATETIME_DATABASE_PATTERN).parse(start)
    }

    @TypeConverter
    fun fromDateToString(start: Date?): String? {
        start ?: return null
        return SimpleDateFormat(Constants.DATETIME_DATABASE_PATTERN).format(start)
    }
}