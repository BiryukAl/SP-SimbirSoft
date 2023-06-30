package ru.simbirsoft.data.cache.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo("date_start")
    val dateStart: Timestamp,
    @ColumnInfo("date_finish")
    val dateFinish: Timestamp,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("description")
    val description: String,
)
