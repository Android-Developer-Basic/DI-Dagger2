package ru.otus.di.domain.data

import java.time.ZonedDateTime

data class Data<out T: Any>(val items: List<T>, val syncedAt: ZonedDateTime)