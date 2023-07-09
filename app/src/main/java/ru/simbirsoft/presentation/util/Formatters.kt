package ru.simbirsoft.presentation.util

import java.text.SimpleDateFormat
import java.util.Locale

class Formatters {
    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    val dateFormatters = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
}