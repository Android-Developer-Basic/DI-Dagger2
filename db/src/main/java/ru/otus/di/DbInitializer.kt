package ru.otus.di

import android.util.Log
import ru.otus.di.domain.AppInitializer

class DbInitializer(private val tag: String) : AppInitializer {
    override fun init() {
        Log.i(tag, "Initializing db...")
    }
}
