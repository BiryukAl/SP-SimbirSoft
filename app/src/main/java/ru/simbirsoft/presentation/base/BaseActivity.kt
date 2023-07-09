package ru.simbirsoft.presentation.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.simbirsoft.R

open class BaseActivity(
    @LayoutRes private val activityLayout: Int,
    @IdRes  val fragmentsContainerId: Int,
) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityLayout)
    }

    fun addFragment(fragment: Fragment, tag: String, detachCurrent: Boolean = false) {
        val transaction = supportFragmentManager.beginTransaction()

        if (detachCurrent) {
            supportFragmentManager.findFragmentById(fragmentsContainerId)?.let { currentFragment ->
                transaction.detach(currentFragment)
            }
        }

        transaction
            .add(fragmentsContainerId, fragment, tag)
            .addToBackStack(null)
            .commit()
    }

    fun addWithRemove(fragment: Fragment, tag: String) {
        val transaction = supportFragmentManager.beginTransaction()
        supportFragmentManager.findFragmentById(fragmentsContainerId)?.let { currentFragment ->
            transaction.remove(currentFragment)
        }
        transaction
            .add(fragmentsContainerId, fragment, tag)
            .addToBackStack(null)
            .commit()
    }

    fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(fragmentsContainerId, fragment, tag)
            .addToBackStack(null)
            .commit()
    }
}