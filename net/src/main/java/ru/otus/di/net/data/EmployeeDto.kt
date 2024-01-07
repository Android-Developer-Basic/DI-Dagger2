package ru.otus.di.net.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.di.domain.data.Employee

@Serializable
data class EmployeeDto(
    val id: Int,
    @SerialName("employee_name")
    val name: String,
    @SerialName("employee_salary")
    val salary: Int,
    @SerialName("employee_age")
    val age: Int
)

fun EmployeeDto.toDomain(): Employee = Employee(id, name, salary, age)