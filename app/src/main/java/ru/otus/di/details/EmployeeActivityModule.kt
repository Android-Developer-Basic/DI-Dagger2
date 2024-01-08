package ru.otus.di.details

import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class EmployeeActivityModule(private val employeeId: Int) {
    @Provides
    @Named("employeeId")
    fun employeeId(): Int = employeeId
}