package ru.otus.di

import android.app.Application
import ru.otus.di.domain.AppInitializer
import javax.inject.Inject

class MyDiApplication : Application() {
    val component: AppComponent = DaggerAppComponent.factory().build(this, "MyDiApplication")

    @Inject
    lateinit var initializers: @JvmSuppressWildcards Set<AppInitializer>

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
        initializers.forEach { it.init() }
    }
}

val Application.component: AppComponent get() = (this as MyDiApplication).component