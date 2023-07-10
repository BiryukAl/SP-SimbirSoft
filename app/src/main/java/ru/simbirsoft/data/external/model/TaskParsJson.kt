package ru.simbirsoft.data.external.model

import com.google.gson.annotations.SerializedName

data class TaskParsJson(
    @SerializedName("id")
    val id: Int,
    @SerializedName("date_start")
    val dateStart: String,
    @SerializedName("date_finish")
    val dateFinish: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String?,
)


/*
{
    "id": 1,
    "date_start": "1688990866634",
    "date_finish": "1688994466635",
    "name": "My task",
    "description": "Описание моегодела"
}
*/
