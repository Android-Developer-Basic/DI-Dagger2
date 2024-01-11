package ru.otus.di

import android.app.Application
import android.content.Context
import ru.otus.di.domain.dao.EmployeeDao
import ru.otus.di.domain.dao.EmployeeDetailsDao
import ru.otus.di.domain.net.EmployeeService
import ru.otus.di.net.EmployeeServiceImpl

class MyDiApplication : Application() {
    val component: AppComponent by lazy {
        ManualAppComponent.Factory.build(this)
    }
}

class ManualAppComponent private constructor(
    private val employeeDaoProvider: () -> EmployeeDao,
    private val employeeDetailsDaoProvider: () -> EmployeeDetailsDao,
    private val employeeServiceProvider: () -> EmployeeService
) : AppComponent {
    override fun employeeDao(): EmployeeDao = employeeDaoProvider()

    override fun employeeDetailsDao(): EmployeeDetailsDao = employeeDetailsDaoProvider()

    override fun employeeService(): EmployeeService = employeeServiceProvider()

    object Factory : AppComponent.Factory {
        override fun build(context: Context): AppComponent {
            // Зависимости DB
            val dbModule = DbModule()
            // Singlton
            val db = dbModule.db(context)

            return ManualAppComponent(
                { dbModule.employeeDao(db) },
                { dbModule.employeeDetailsDao(db) },
                { EmployeeServiceImpl() }
            )
        }
    }
}