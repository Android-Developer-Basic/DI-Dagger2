package ru.otus.di.details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import ru.otus.di.MyDiApplication
import ru.otus.di.domain.dao.EmployeeDetailsDao
import ru.otus.di.domain.data.Employee

class EmployeeViewModel(
    employeesDao: EmployeeDetailsDao,
    employeeId: Int,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    val data: StateFlow<Employee?> = employeesDao
        .getEmployee(employeeId)
        .flowOn(ioDispatcher)
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    class Factory(application: Application, private val employeeId: Int) : ViewModelProvider.Factory {
        private val diApp = application as MyDiApplication

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = EmployeeViewModel(
            diApp.component.employeeDetailsDao(),
            employeeId
        ) as T
    }
}