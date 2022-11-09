package jp.co.archive_asia.onedaycouplediary.viewmodel

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.archive_asia.onedaycouplediary.database.WriteDatabase
import jp.co.archive_asia.onedaycouplediary.model.Write
import jp.co.archive_asia.onedaycouplediary.repository.WriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalendarViewModel(application: Activity) : ViewModel() {

    private val writeDao = WriteDatabase.getDatabase(application).writeDao()
    private val repository: WriteRepository = WriteRepository(writeDao)

    // val getAllData: LiveData<List<Write>> = repository.getAllData.asLiveData()

    private var _currentData = MutableLiveData<List<Write>>()
    val currentData: LiveData<List<Write>> = _currentData

    fun readDateData(date: String): LiveData<List<Write>> {
        viewModelScope.launch(Dispatchers.IO) {
            _currentData.postValue(repository.readDateData(date))
        }
        return _currentData
    }

}

