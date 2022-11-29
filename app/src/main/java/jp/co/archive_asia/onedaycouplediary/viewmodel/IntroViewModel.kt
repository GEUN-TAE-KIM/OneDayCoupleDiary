package jp.co.archive_asia.onedaycouplediary.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.co.archive_asia.onedaycouplediary.firestore.repository.UserRepository
import jp.co.archive_asia.onedaycouplediary.firestore.response.EmptyResult

class IntroViewModel : ViewModel() {

    val loginAnonymously: MutableLiveData<Unit> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    private val userRepository : UserRepository by lazy {
        UserRepository()
    }

    fun signInAnonymously() {
        userRepository.signInAnonymously {
            when(it) {
                is EmptyResult.Success -> {
                    loginAnonymously.value = Unit
                }
                is EmptyResult.Error -> {
                    errorMessage.value = it.message
                }
            }
        }
    }
}