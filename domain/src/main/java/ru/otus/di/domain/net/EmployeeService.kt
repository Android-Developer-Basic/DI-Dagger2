package ru.otus.di.domain.net

import ru.otus.di.domain.data.Data
import ru.otus.di.domain.data.Employee

interface EmployeeService {
    /**
     * Получить список работников
     */
    suspend fun getEmployees(): Result<Data<Employee>>
}