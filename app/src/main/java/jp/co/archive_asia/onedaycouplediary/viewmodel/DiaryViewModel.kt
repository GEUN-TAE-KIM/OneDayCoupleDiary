package jp.co.archive_asia.onedaycouplediary.viewmodel

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import jp.co.archive_asia.onedaycouplediary.database.DiaryDatabase
import jp.co.archive_asia.onedaycouplediary.model.Diary
import jp.co.archive_asia.onedaycouplediary.repository.DiaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiaryViewModel(application: Activity) : ViewModel() {

    private val writeDao = DiaryDatabase.getDatabase(application).writeDao()
    private val repository: DiaryRepository = DiaryRepository(writeDao)

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

    fun searchDatabase(searchQuery: String): LiveData<List<Diary>> {
        return repository.searchDatabase(searchQuery).asLiveData()
    }

}