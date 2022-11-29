package jp.co.archive_asia.onedaycouplediary.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.co.archive_asia.onedaycouplediary.firestore.repository.DiaryRepository
import jp.co.archive_asia.onedaycouplediary.firestore.repository.UserRepository
import jp.co.archive_asia.onedaycouplediary.firestore.response.ResultStatus
import jp.co.archive_asia.onedaycouplediary.model.Diary

class DiaryViewModel() : ViewModel() {

    private val repository: DiaryRepository by lazy { DiaryRepository() }

    private val _addNote = MutableLiveData<ResultStatus<Pair<Diary, String>>>()
    private val _updateNote = MutableLiveData<ResultStatus<String>>()
    private val _deleteNote = MutableLiveData<ResultStatus<String>>()

    fun addData(diary: Diary) {
        diary.user_id = UserRepository().currentUser!!.uid
        repository.addData(diary) {
            _addNote.value
        }

    }

    fun updateData(diary: Diary) {

        repository.updateData(diary) {
            _updateNote.value
        }

    }

    fun deleteData(diary: Diary) {

        repository.deleteData(diary) {
            _deleteNote.value
        }

    }


}