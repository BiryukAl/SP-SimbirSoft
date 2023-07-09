package ru.simbirsoft.data.cache.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int? = null,
    @ColumnInfo("date_start")
    val dateStart: Long,
    @ColumnInfo("date_finish")
    val dateFinish: Long,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("description")
    val description: String?,
)
