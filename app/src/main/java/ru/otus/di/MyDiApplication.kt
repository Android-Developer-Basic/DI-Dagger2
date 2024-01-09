package ru.otus.di

import android.app.Application
import android.util.Log
import ru.otus.di.domain.AppInitializer
import javax.inject.Inject

private const val TAG = "MyDiApplication"

class MyDiApplication : Application() {
    val component: AppComponent = DaggerAppComponent.factory().build(this, TAG)

    @Inject
    lateinit var initializers: @JvmSuppressWildcards Map<String, AppInitializer>

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
        initializers.forEach { (key, value) ->
            Log.i(TAG, "Running initializer: $key...")
            value.init()
        }
    }
}

val Application.component: AppComponent get() = (this as MyDiApplication).component