package ru.otus.di.details

import dagger.Subcomponent
import ru.otus.di.ActivityScope

@ActivityScope
@Subcomponent
interface EmployeeActivitySubcomponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): EmployeeActivitySubcomponent
    }

    fun inject(activity: EmployeeActivity)
}