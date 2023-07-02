package ru.simbirsoft

import android.app.Application
import android.content.Context
import ru.simbirsoft.data.MapperEntity
import ru.simbirsoft.data.TaskRepositoryImpl
import ru.simbirsoft.data.cache.TaskDatabase
import ru.simbirsoft.data.cache.TaskDatabaseImpl
import ru.simbirsoft.di.ServiceLocator
import ru.simbirsoft.di.locate
import ru.simbirsoft.domain.repository.TaskRepository
import ru.simbirsoft.domain.usecase.AddFromExternalUseCase
import ru.simbirsoft.domain.usecase.AddTaskUseCase
import ru.simbirsoft.domain.usecase.GetAllTaskUseCase

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        ServiceLocator.register<Context>(this)

        // Data
        ServiceLocator.register<TaskDatabase>(TaskDatabaseImpl.create(locate()))

        ServiceLocator.register(MapperEntity())

        ServiceLocator.register<TaskRepository>(TaskRepositoryImpl(locate(), locate()))

        // UseCAse
        ServiceLocator.register(AddTaskUseCase(locate()))
        ServiceLocator.register(GetAllTaskUseCase(locate()))
        ServiceLocator.register(AddFromExternalUseCase(locate()))


    }
}
