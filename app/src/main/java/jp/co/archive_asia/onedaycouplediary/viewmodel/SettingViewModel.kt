package jp.co.archive_asia.onedaycouplediary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import jp.co.archive_asia.onedaycouplediary.firestore.repository.UserRepository

class SettingViewModel : ViewModel() {

    val isAnonymous: LiveData<Boolean> = liveData {
        emit(userRepository.currentUser?.isAnonymous ?: false)
    }
    val logout: MutableLiveData<Boolean> = MutableLiveData()

    private val userRepository: UserRepository by lazy {
        UserRepository()
    }

    fun signOut() {
        userRepository.logout {
            logout.value = true
        }
    }

}