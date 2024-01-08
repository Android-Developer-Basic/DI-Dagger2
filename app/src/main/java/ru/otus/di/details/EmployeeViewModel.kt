package ru.otus.di.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import ru.otus.di.domain.dao.EmployeeDetailsDao
import ru.otus.di.domain.data.Employee
import javax.inject.Inject
import javax.inject.Named

class EmployeeViewModel(
    employeesDao: EmployeeDetailsDao,
    employeeId: Int,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    val data: StateFlow<Employee?> = employeesDao
        .getEmployee(employeeId)
        .flowOn(ioDispatcher)
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    class Factory @Inject constructor(
        private val employeeDetailsDao: EmployeeDetailsDao,
        @Named("employeeId") private val employeeId: Int
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = EmployeeViewModel(
            employeeDetailsDao,
            employeeId
        ) as T
    }
}