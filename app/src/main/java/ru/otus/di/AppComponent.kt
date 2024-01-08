package ru.otus.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.otus.di.domain.dao.EmployeeDao
import ru.otus.di.domain.dao.EmployeeDetailsDao
import javax.inject.Singleton

@Singleton
@Component(modules = [DbModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun build(@BindsInstance context: Context): AppComponent
    }

    fun employeeDao(): EmployeeDao

    fun employeeDetailsDao(): EmployeeDetailsDao
}