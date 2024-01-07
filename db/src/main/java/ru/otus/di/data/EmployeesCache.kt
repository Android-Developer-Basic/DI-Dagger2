package ru.otus.di.data

import androidx.room.Embedded
import androidx.room.Relation

internal data class EmployeesCache(
    @Embedded
    val cache: CacheEntity,
    @Relation(
        parentColumn = "name",
        entityColumn = "cacheName"
    )
    val employees: List<EmployeeEntity>
)