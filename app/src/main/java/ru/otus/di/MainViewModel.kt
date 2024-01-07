package ru.otus.di

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.otus.di.domain.dao.EmployeeDao
import ru.otus.di.domain.data.Data
import ru.otus.di.domain.data.Employee
import ru.otus.di.domain.net.EmployeeService

class MainViewModel(
    private val employeesDao: EmployeeDao,
    private val network: EmployeeService
) : ViewModel() {

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

    class Factory(application: Application) : ViewModelProvider.Factory {
        private val diApp = application as MyDiApplication

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(
            diApp.employeeDao,
            diApp.net
        ) as T
    }
}