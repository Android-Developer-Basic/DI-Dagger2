package ru.otus.di.net

import dagger.Binds
import dagger.Module
import ru.otus.di.domain.net.EmployeeService

@Module
interface NetModule {
    @Binds
    fun employeeService(impl: EmployeeServiceImpl): EmployeeService
}