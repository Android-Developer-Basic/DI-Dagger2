package ru.otus.di.net

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET
import ru.otus.di.domain.data.Data
import ru.otus.di.domain.data.Employee
import ru.otus.di.domain.net.EmployeeService
import ru.otus.di.net.data.EmployeeDto
import ru.otus.di.net.data.toDomain
import java.time.ZonedDateTime
import java.util.concurrent.TimeUnit

class EmployeeServiceImpl : EmployeeService {
    private val okHttp = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    private val retrofit = Retrofit.Builder()
        .client(okHttp)
        .baseUrl("https://dummy.restapiexample.com/api/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    private val api: Api get() = retrofit.create(Api::class.java)

    /**
     * Получить список работников
     */
    override suspend fun getEmployees(): Result<Data<Employee>> = runCatching {
        Data(api.getEmployees().data.map { it.toDomain() }, ZonedDateTime.now())
    }
}

@Serializable
data class DataResult(
    val status: String,
    val data: List<EmployeeDto>
)

private interface Api {
    @GET("v1/employees")
    suspend fun getEmployees(): DataResult
}