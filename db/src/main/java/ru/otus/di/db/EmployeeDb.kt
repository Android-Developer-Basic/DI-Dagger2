package ru.otus.di.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.otus.di.data.CacheEntity
import ru.otus.di.data.EmployeeEntity

@Database(
    entities = [CacheEntity::class, EmployeeEntity::class],
    version = 1
)
@TypeConverters(
    value = [Converters::class]
)
abstract class EmployeeDb : RoomDatabase() {
    abstract fun employees(): EmployeeDaoImpl
}