package ru.simbirsoft.data.cache

import ru.simbirsoft.data.cache.dao.TaskDao

interface TaskDatabase {
    val taskDao: TaskDao
}