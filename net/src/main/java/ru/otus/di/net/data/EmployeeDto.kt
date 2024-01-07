package ru.otus.di.net.data

import kotlinx.serialization.Serializable
import ru.otus.di.domain.data.Employee

@Serializable
data class EmployeeDto(
    val id: Int,
    val name: String,
    val salary: Int,
    val age: Int
)

fun EmployeeDto.toDomain(): Employee = Employee(id, name, salary, age)