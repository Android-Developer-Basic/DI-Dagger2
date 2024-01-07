package ru.otus.di.data

import androidx.room.Entity
import androidx.room.ForeignKey
import ru.otus.di.db.EmployeeDaoImpl
import ru.otus.di.domain.data.Employee

@Entity(
    tableName = "Employees",
    primaryKeys = ["cacheName", "id"],
    foreignKeys = [
        ForeignKey(
            CacheEntity::class,
            ["name"],
            ["cacheName"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
internal data class EmployeeEntity(
    val cacheName: String,
    val id: Int,
    val name: String,
    val salary: Int,
    val age: Int
)

internal fun EmployeeEntity.toDomain(): Employee = Employee(id, name, salary, age)
internal fun Employee.toEntity(): EmployeeEntity = EmployeeEntity(EmployeeDaoImpl.CACHE_NAME, id, name, salary, age)