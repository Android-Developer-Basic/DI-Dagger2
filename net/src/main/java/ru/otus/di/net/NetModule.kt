package ru.otus.di.net

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import ru.otus.di.domain.AppInitializer
import ru.otus.di.domain.net.EmployeeService

@Module
interface NetModule {
    @Binds
    fun employeeService(impl: EmployeeServiceImpl): EmployeeService

    @Binds
    @IntoMap
    @StringKey("NET")
    fun netInitializer(impl: NetInitializer): AppInitializer
}