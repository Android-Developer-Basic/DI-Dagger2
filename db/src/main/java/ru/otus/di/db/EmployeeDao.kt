package ru.otus.di.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.otus.di.data.CacheEntity
import ru.otus.di.data.EmployeeEntity
import ru.otus.di.data.EmployeesCache
import ru.otus.di.data.toDomain
import ru.otus.di.data.toEntity
import ru.otus.di.domain.data.Data
import ru.otus.di.domain.data.Employee

@Dao
abstract class EmployeeDao {
    /**
     * Записать новые данные
     */
    @Transaction
    open fun replace(employees: Data<Employee>) {
        delete()
        insert(CacheEntity(CACHE_NAME, employees.syncedAt))
        insert(employees.items.map { it.toEntity() })
    }

    /**
     * Прочитать список
     */
    fun getList(): Flow<Data<Employee>?> = getAll().map { records ->
        records.firstOrNull()?.let { record ->
            Data(record.employees.map { it.toDomain() }, record.cache.syncDateTime)
        }
    }

    /**
     * Прочитать работника по [id]
     */
    fun getEmployee(id: Int): Flow<Employee?> = getById(id).map { records ->
        records.firstOrNull()?.toDomain()
    }

    @Query("SELECT * FROM Cache WHERE name='$CACHE_NAME'")
    internal abstract fun getAll(): Flow<List<EmployeesCache>>

    @Query("SELECT * FROM Employees WHERE id=:id")
    internal abstract fun getById(id: Int): Flow<List<EmployeeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    internal abstract fun insert(cache: CacheEntity)

    @Insert
    internal abstract fun insert(employees: List<EmployeeEntity>)

    @Query("DELETE FROM Cache WHERE name='$CACHE_NAME'")
    internal abstract fun delete()

    companion object {
        internal const val CACHE_NAME = "employees"
    }
}
