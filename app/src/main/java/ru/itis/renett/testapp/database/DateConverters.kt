package ru.itis.renett.testapp.database

import androidx.room.TypeConverter
import java.util.*

class DateConverters {
    @TypeConverter
    fun dateFromTimestamp(value: Long?): Date? {
        return value?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
