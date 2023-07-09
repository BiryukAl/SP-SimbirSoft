package ru.simbirsoft.presentation

import android.os.Bundle
import ru.simbirsoft.R
import ru.simbirsoft.presentation.base.BaseActivity
import ru.simbirsoft.presentation.screens.allTask.AllTaskFragment

class MainActivity : BaseActivity(
    activityLayout = R.layout.activity_main,
    fragmentsContainerId = R.id.main_fragments_container,
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .add(
                    fragmentsContainerId,
                    AllTaskFragment.getInstance(),
                    AllTaskFragment.ALL_TASK_FRAGMENT_TAG
                )
                .commit()
        }
    }
}
