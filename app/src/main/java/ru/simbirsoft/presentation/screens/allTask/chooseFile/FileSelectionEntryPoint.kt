package ru.simbirsoft.presentation.screens.allTask.chooseFile

import androidx.fragment.app.Fragment
import java.io.FileDescriptor

interface FileSelectionEntryPoint {
    val fileSelectionOwner: Fragment
    fun onFileSelected(fileDescriptor: FileDescriptor?)
}