package ru.otus.di.main

import android.app.Activity
import androidx.recyclerview.widget.ListAdapter
import dagger.Module
import dagger.Provides
import ru.otus.di.ActivityScope
import ru.otus.di.details.EmployeeActivity
import ru.otus.di.domain.data.Employee

@Module
class MainActivityModule {
    @Provides
    @ActivityScope
    fun adapter(activity: Activity): ListAdapter<Employee, EmployeeAdapter.Holder> = EmployeeAdapter {
        EmployeeActivity.launch(activity, it)
    }
}