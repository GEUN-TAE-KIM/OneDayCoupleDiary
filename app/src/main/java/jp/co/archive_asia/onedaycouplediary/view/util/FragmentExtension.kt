package jp.co.archive_asia.onedaycouplediary.view.util

import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import jp.co.archive_asia.onedaycouplediary.R

fun Fragment.addFinishAppBackButtonCallback() {
    requireActivity().onBackPressedDispatcher.addCallback(this) {
        val navHostFragment = parentFragmentManager.findFragmentById(R.id.container_main)
        val backStackEntryCount = navHostFragment?.childFragmentManager?.fragments?.size
        if (backStackEntryCount == 0) {
            requireActivity().finish()
        }
    }
}