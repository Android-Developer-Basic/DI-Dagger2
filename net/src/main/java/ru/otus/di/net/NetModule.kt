package ru.otus.di.net

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import ru.otus.di.domain.AppInitializer
import ru.otus.di.domain.net.EmployeeService

@Module
interface NetModule {
    @Binds
    fun employeeService(impl: EmployeeServiceImpl): EmployeeService

    @Binds
    @IntoSet
    fun netInitializer(impl: NetInitializer): AppInitializer
}