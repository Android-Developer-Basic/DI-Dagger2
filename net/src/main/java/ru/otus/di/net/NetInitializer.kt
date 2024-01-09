package ru.otus.di.net

import android.util.Log
import ru.otus.di.domain.AppInitializer
import javax.inject.Inject
import javax.inject.Named

class NetInitializer @Inject constructor(@Named("tag") private val tag: String) : AppInitializer {
    override fun init() {
        Log.i(tag, "Initializing net...")
    }
}
