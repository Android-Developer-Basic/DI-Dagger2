package ru.otus.di

import android.app.Application
import androidx.room.Room
import ru.otus.di.db.EmployeeDb
import ru.otus.di.domain.dao.EmployeeDao

class MyDiApplication : Application() {
    private val db: EmployeeDb by lazy {
        Room.databaseBuilder(this, EmployeeDb::class.java,"employees")
            .fallbackToDestructiveMigration()
            .build()
    }

    val employeeDao: EmployeeDao get() = db.employees()
}