package ru.simbirsoft.presentation.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes idLayout: Int) : Fragment(idLayout) {

    fun replaceFragment(fragment: Fragment, tag: String) {
        (requireActivity() as? BaseActivity)?.replaceFragment(fragment, tag)
    }
}
