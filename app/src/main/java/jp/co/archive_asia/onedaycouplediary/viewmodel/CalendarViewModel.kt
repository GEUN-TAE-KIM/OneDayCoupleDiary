package jp.co.archive_asia.onedaycouplediary.viewmodel

import android.app.Activity
import androidx.lifecycle.*
import jp.co.archive_asia.onedaycouplediary.database.WriteDatabase
import jp.co.archive_asia.onedaycouplediary.model.Write
import jp.co.archive_asia.onedaycouplediary.repository.WriteRepository

class CalendarViewModel(application: Activity) : ViewModel() {

    private val writeDao = WriteDatabase.getDatabase(application).writeDao()
    private val repository: WriteRepository = WriteRepository(writeDao)

    val getAllData: LiveData<List<Write>> = repository.getAllData.asLiveData()

}