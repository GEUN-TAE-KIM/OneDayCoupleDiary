package jp.co.archive_asia.onedaycouplediary.viewmodel

import android.app.Activity
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.model.Diary
import jp.co.archive_asia.onedaycouplediary.repository.DiaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiaryViewModel(application: Activity) : ViewModel() {

    private val repository: DiaryRepository by lazy { DiaryRepository() }

    fun addData(diary: Diary) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addData(diary)
        }
    }

    fun updateData(diary: Diary) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(diary)
        }
    }

    fun deleteData(diary: Diary) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(diary)
        }
    }

}