package jp.co.archive_asia.onedaycouplediary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.archive_asia.onedaycouplediary.model.Write
import jp.co.archive_asia.onedaycouplediary.repository.WriteRepository
import kotlinx.coroutines.launch

class WriteViewModel(private val repository: WriteRepository) : ViewModel() {

    private val _allData = MutableLiveData<List<Write>>()
    val allData: LiveData<List<Write>> = _allData

    init {
        loadAllData()
    }

    private fun loadAllData() {
        viewModelScope.launch {
            repository.getAllData.collect {
                _allData.value = it
            }
        }
    }

}