package ru.otus.di.main

import dagger.Component
import ru.otus.di.ActivityScope
import ru.otus.di.AppComponent

@ActivityScope
@Component(dependencies = [AppComponent::class])
interface MainActivityComponent {
    @Component.Factory
    interface Factory {
        fun build(appComponent: AppComponent): MainActivityComponent
    }

    fun vmFactory(): MainViewModel.Factory
}