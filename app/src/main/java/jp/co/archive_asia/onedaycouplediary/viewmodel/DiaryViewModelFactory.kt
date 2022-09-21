package jp.co.archive_asia.onedaycouplediary.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class DiaryViewModelFactory(private val application: Activity) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(DiaryViewModel::class.java)) {
            return DiaryViewModel(application) as T
        }
        throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")

    }

}