package ru.simbirsoft.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.simbirsoft.R
import ru.simbirsoft.presentation.screens.allTask.AllTaskFragment

class MainActivity : AppCompatActivity() {

    private val fragmentsContainerId: Int = R.id.main_fragments_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(
                fragmentsContainerId,
                AllTaskFragment.getInstance(),
                AllTaskFragment.ALL_TASK_FRAGMENT_TAG
            ).commit()
    }
}
