package ru.otus.di

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.otus.di.db.EmployeeDb
import ru.otus.di.domain.data.Data
import ru.otus.di.domain.data.Employee
import ru.otus.di.net.EmployeeService

class MainViewModel(application: Application) : AndroidViewModel(application) {
    // Зависимость 1
    private val db = Room.databaseBuilder(application, EmployeeDb::class.java,"employees")
        .fallbackToDestructiveMigration()
        .build()

    // Зависимость 2
    private val employeesDao = db.employees()

    // Зависимость 3
    private val network = EmployeeService()

    val data: StateFlow<Data<Employee>?> = employeesDao
        .getList()
        .flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    fun load() {
        viewModelScope.launch {
            Log.i(TAG, "Loading employees...")
            withContext(Dispatchers.IO) {
                network.getEmployees()
                    .onSuccess {
                        Log.i(TAG, "Loaded some employees")
                        employeesDao.replace(it)
                    }
                    .onFailure {
                        Log.w(TAG, "Error loading employees", it)
                    }
            }
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}