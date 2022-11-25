package jp.co.archive_asia.onedaycouplediary.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.co.archive_asia.onedaycouplediary.firestore.repository.UserRepository
import jp.co.archive_asia.onedaycouplediary.firestore.response.EmptyResult

class JoinViewModel : ViewModel() {

    val result: MutableLiveData<Unit> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    private val userRepository : UserRepository by lazy {
        UserRepository()
    }

    fun createAccount(email: String, password: String) {
        userRepository.signUpEmail(email, password) {
            when (it) {
                is EmptyResult.Success -> {
                    result.value = Unit
                }
                is EmptyResult.Error -> {
                    errorMessage.value = it.message
                }
            }
        }
    }
}