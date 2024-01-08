package ru.otus.di

import android.app.Application
import androidx.room.Room
import ru.otus.di.db.EmployeeDb
import ru.otus.di.domain.dao.EmployeeDao
import ru.otus.di.domain.dao.EmployeeDetailsDao
import ru.otus.di.domain.net.EmployeeService
import ru.otus.di.net.EmployeeServiceImpl

class MyDiApplication : Application() {
    private val db: EmployeeDb by lazy {
        Room.databaseBuilder(this, EmployeeDb::class.java,"employees")
            .fallbackToDestructiveMigration()
            .build()
    }

    val net: EmployeeService by lazy {
        EmployeeServiceImpl()
    }

    val employeeDao: EmployeeDao get() = db.employees()

    val employeeDetailsDao: EmployeeDetailsDao get() = db.employees()
}