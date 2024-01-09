package ru.otus.di.main

import android.app.Activity
import androidx.recyclerview.widget.ListAdapter
import dagger.BindsInstance
import dagger.Component
import ru.otus.di.ActivityScope
import ru.otus.di.AppComponent
import ru.otus.di.domain.data.Employee

@ActivityScope
@Component(
    dependencies = [AppComponent::class],
    modules = [MainActivityModule::class]
)
interface MainActivityComponent {
    @Component.Factory
    interface Factory {
        fun build(appComponent: AppComponent, @BindsInstance activity: Activity): MainActivityComponent
    }

    fun vmFactory(): MainViewModel.Factory

    fun adapter(): ListAdapter<Employee, EmployeeAdapter.Holder>
}