package ru.otus.di.db

import androidx.room.TypeConverter
import java.time.ZonedDateTime

internal class Converters {
    @TypeConverter
    fun toDateTime(string: String?): ZonedDateTime? {
        return string?.let { ZonedDateTime.parse(string) }
    }

    @TypeConverter
    fun fromDateTime(dateTime: ZonedDateTime?): String? {
        return dateTime?.toString()
    }
}