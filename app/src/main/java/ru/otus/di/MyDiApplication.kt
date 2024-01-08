package ru.otus.di

import android.app.Application

class MyDiApplication : Application() {
    val component: AppComponent = DaggerAppComponent.factory().build(this)
}

val Application.component: AppComponent get() = (this as MyDiApplication).component