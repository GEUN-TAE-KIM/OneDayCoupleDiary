package jp.co.archive_asia.onedaycouplediary.viewmodel

import android.app.Activity
import android.app.Application
import androidx.lifecycle.*
import jp.co.archive_asia.onedaycouplediary.database.WriteDatabase
import jp.co.archive_asia.onedaycouplediary.model.Write
import jp.co.archive_asia.onedaycouplediary.repository.WriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalendarViewModel(application: Activity) : ViewModel() {

    private val writeDao = WriteDatabase.getDatabase(application).writeDao()
    private val repository: WriteRepository = WriteRepository(writeDao)

    val getAllData: LiveData<List<Write>> = repository.getAllData.asLiveData()

    fun addData(write: Write) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addData(write)
        }
    }

    fun updateData(write: Write) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(write)
        }
    }

    fun deleteData(write: Write) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(write)
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Write>> {
        return repository.searchDatabase(searchQuery).asLiveData()
    }

}