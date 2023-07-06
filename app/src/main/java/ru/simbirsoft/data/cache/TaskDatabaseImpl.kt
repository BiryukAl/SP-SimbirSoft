package ru.simbirsoft.data.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.simbirsoft.data.cache.entities.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 1
)
abstract class TaskDatabaseImpl : RoomDatabase(), TaskDatabase {

    companion object {
        fun create(context: Context) = Room
            .databaseBuilder(
                context,
                TaskDatabaseImpl::class.java,
                "task-database.db"
            )
            .build()
    }
}