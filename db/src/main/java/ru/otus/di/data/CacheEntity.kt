package ru.otus.di.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.ZonedDateTime

@Entity(tableName = "Cache")
internal data class CacheEntity internal constructor(@PrimaryKey val name: String, val syncDateTime: ZonedDateTime)