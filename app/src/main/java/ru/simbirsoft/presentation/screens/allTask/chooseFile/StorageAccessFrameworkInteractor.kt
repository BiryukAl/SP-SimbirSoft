package ru.simbirsoft.presentation.screens.allTask.chooseFile

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher

class StorageAccessFrameworkInteractor(private val fileSelectionEntryPoint: FileSelectionEntryPoint) {

    private val selectFileLauncher: ActivityResultLauncher<SelectFileParams> =
        fileSelectionEntryPoint.fileSelectionOwner
            .registerForActivityResult(SelectFileResultContract()) { uri ->
                onFileSelectionFinished(uri)
            }

    fun beginSelectingFile(selectFileParams: SelectFileParams) =
        selectFileLauncher.launch(selectFileParams)

    @SuppressLint("Recycle")
    private fun onFileSelectionFinished(fileUri: Uri?) {
        val fileDescriptor = fileUri?.let { uri ->
            fileSelectionEntryPoint.fileSelectionOwner
                .requireContext()
                .contentResolver
                .openFileDescriptor(uri, "r")
                ?.fileDescriptor
        }

        fileSelectionEntryPoint.onFileSelected(fileDescriptor)
    }
}
