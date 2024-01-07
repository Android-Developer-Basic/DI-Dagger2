package ru.otus.di

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import ru.otus.di.domain.dao.EmployeeDao
import ru.otus.di.domain.data.Data
import ru.otus.di.domain.data.Employee
import ru.otus.di.domain.net.EmployeeService
import java.time.ZonedDateTime
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class MainViewModelTest {

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun updatesWhenDbUpdates() = runTest {
        val dispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val dao = FakeDao()
        val model = MainViewModel(dao, FakeService(data), dispatcher)

        val updates = mutableListOf<Data<Employee>?>()
        val updatesJob = launch(dispatcher) {
            model.data.collect {
                updates.add(it)
            }
        }

        dao.stored.emit(data)

        assertEquals(
            listOf(null, data),
            updates
        )

        updatesJob.cancelAndJoin()
    }

    @Test
    fun updatesDaoWithNetwork() = runTest {
        val dispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val dao = FakeDao()
        val service = FakeService(data)
        val model = MainViewModel(dao, service, dispatcher)

        model.load()

        assertEquals(
            listOf(data),
            dao.updates
        )

        assertEquals(1, service.getCalls)
    }

    private companion object {
        val employee = Employee(1, "Vasya", 100500, 18)
        val data: Data<Employee> = Data(
            listOf(employee),
            ZonedDateTime.now()
        )
    }
}

internal class FakeDao : EmployeeDao {
    val updates = mutableListOf<Data<Employee>>()
    override fun replace(employees: Data<Employee>) {
        updates.add(employees)
    }

    val stored = MutableSharedFlow<Data<Employee>?>()
    override fun getList(): Flow<Data<Employee>?> = stored
}

internal class FakeService(private val data: Data<Employee>) : EmployeeService {
    var getCalls = 0
    override suspend fun getEmployees(): Result<Data<Employee>> {
        ++getCalls
        return Result.success(data)
    }
}