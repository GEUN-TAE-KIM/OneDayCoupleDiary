package jp.co.archive_asia.onedaycouplediary.view.util

import android.content.DialogInterface
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}

fun Fragment.showConfirmDialog(
    @StringRes title: Int? = null,
    @StringRes message: Int? = null,
    @StringRes positiveButtonText: Int? = null,
    positiveButtonListener: ((DialogInterface, Int) -> Unit)? = null,
    @StringRes negativeButtonText: Int? = null,
    negativeButtonListener: ((DialogInterface, Int) -> Unit)? = null,

) {
    val builder = MaterialAlertDialogBuilder(requireContext())
    title?.let { builder.setTitle(it) }
    message?.let { builder.setMessage(it) }
    positiveButtonText?.let { builder.setPositiveButton(it, positiveButtonListener) }
    negativeButtonText?.let { builder.setNegativeButton(it, negativeButtonListener) }
    builder.show()
}