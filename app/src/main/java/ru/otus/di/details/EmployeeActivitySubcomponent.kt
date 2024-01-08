package ru.otus.di.details

import dagger.Subcomponent
import ru.otus.di.ActivityScope

@ActivityScope
@Subcomponent(modules = [EmployeeActivityModule::class])
interface EmployeeActivitySubcomponent {

    @Subcomponent.Builder
    interface Builder {
        fun employeeModule(m: EmployeeActivityModule): Builder
        fun build(): EmployeeActivitySubcomponent
    }

    fun inject(activity: EmployeeActivity)
}