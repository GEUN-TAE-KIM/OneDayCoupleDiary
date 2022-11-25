package jp.co.archive_asia.onedaycouplediary.firestore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.archive_asia.onedaycouplediary.firestore.models.User
import jp.co.archive_asia.onedaycouplediary.firestore.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val userRepository : UserRepository by lazy {
        UserRepository()
    }

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.Main) {
            userRepository.addNewUser(user)
        }
    }
}