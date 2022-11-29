package jp.co.archive_asia.onedaycouplediary.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.co.archive_asia.onedaycouplediary.firestore.repository.UserRepository

class SplashViewModel : ViewModel() {

    val isLogin: MutableLiveData<Boolean> = MutableLiveData()

    private val userRepository : UserRepository by lazy {
        UserRepository()
    }

    fun checkLogin() {
        val currentUser = userRepository.currentUser
        isLogin.value = currentUser != null
    }
}