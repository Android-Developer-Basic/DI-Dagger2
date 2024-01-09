package ru.otus.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.otus.di.details.EmployeeActivitySubcomponent
import ru.otus.di.domain.dao.EmployeeDao
import ru.otus.di.domain.net.EmployeeService
import ru.otus.di.net.NetModule
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [DbModule::class, NetModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun build(
            @BindsInstance context: Context,
            @Named("tag") @BindsInstance tag: String
        ): AppComponent
    }

    fun employeeDao(): EmployeeDao

    fun employeeService(): EmployeeService

    fun employeeActivityComponent(): EmployeeActivitySubcomponent.Builder

    fun inject(app: MyDiApplication)
}