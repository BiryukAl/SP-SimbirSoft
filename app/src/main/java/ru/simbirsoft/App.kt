package ru.simbirsoft

import android.app.Application
import android.content.Context
import ru.simbirsoft.data.MapperEntity
import ru.simbirsoft.data.cache.TaskDatabase
import ru.simbirsoft.data.cache.TaskDatabaseImpl
import ru.simbirsoft.di.ServiceLocator
import ru.simbirsoft.di.locate
import ru.simbirsoft.domain.usecase.AddFromExternalUseCase
import ru.simbirsoft.domain.usecase.AddTaskUseCase
import ru.simbirsoft.domain.usecase.GetAllTaskUseCase
import ru.simbirsoft.domain.usecase.GetTaskOnDayUseCase
import ru.simbirsoft.presentation.MapperUi
import ru.simbirsoft.presentation.util.Formatters

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        ServiceLocator.register<Context>(this)

        // Data
        ServiceLocator.register<TaskDatabase>(TaskDatabaseImpl.create(locate()))
//        ServiceLocator.register<Gson>(Gson())
        ServiceLocator.register<MapperEntity>(MapperEntity())

//        ServiceLocator.register<TaskRepository>(TaskRepositoryImpl(locate(), locate(), locate()))


        // UseCAse
        ServiceLocator.register<AddTaskUseCase>(AddTaskUseCase(locate()))
        ServiceLocator.register<GetAllTaskUseCase>(GetAllTaskUseCase(locate()))
        ServiceLocator.register<GetTaskOnDayUseCase>(GetTaskOnDayUseCase(locate()))
        ServiceLocator.register<AddFromExternalUseCase>(AddFromExternalUseCase(locate()))

        ServiceLocator.register<Formatters>(Formatters())
        ServiceLocator.register<MapperUi>(MapperUi(locate()))

    }
}
