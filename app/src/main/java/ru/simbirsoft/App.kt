package ru.simbirsoft

import android.app.Application
import android.content.Context
import ru.simbirsoft.data.MapperEntity
import ru.simbirsoft.data.TaskRepositoryImpl
import ru.simbirsoft.data.cache.TaskDatabase
import ru.simbirsoft.data.cache.TaskDatabaseImpl
import ru.simbirsoft.di.ServiceLocator
import ru.simbirsoft.di.locate

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        ServiceLocator.register<Context>(this)

        ServiceLocator.register<TaskDatabase>(TaskDatabaseImpl.create(locate()))

        ServiceLocator.register(MapperEntity())

        ServiceLocator.register(TaskRepositoryImpl(locate(), locate()))
    }
}