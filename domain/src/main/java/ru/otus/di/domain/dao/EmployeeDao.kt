package ru.otus.di.domain.dao

import kotlinx.coroutines.flow.Flow
import ru.otus.di.domain.data.Data
import ru.otus.di.domain.data.Employee

interface EmployeeDao {
    /**
     * Записать новые данные
     */
    fun replace(employees: Data<Employee>)

    /**
     * Прочитать список
     */
    fun getList(): Flow<Data<Employee>?>
}

interface EmployeeDetailsDao {
    /**
     * Прочитать работника по [id]
     */
    fun getEmployee(id: Int): Flow<Employee?>
}