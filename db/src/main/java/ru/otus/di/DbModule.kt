package ru.otus.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.otus.di.db.EmployeeDb
import ru.otus.di.domain.dao.EmployeeDao
import ru.otus.di.domain.dao.EmployeeDetailsDao
import javax.inject.Singleton

@Module
class DbModule {
    @Provides
    @Singleton
    fun db(context: Context): EmployeeDb = Room.databaseBuilder(context, EmployeeDb::class.java,"employees")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun employeeDao(db: EmployeeDb): EmployeeDao = db.employees()

    @Provides
    fun employeeDetailsDao(db: EmployeeDb): EmployeeDetailsDao = db.employees()
}
