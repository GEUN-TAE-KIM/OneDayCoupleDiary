package jp.co.archive_asia.onedaycouplediary.firestore.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import jp.co.archive_asia.onedaycouplediary.firestore.Constants.USERS
import jp.co.archive_asia.onedaycouplediary.firestore.models.User
import jp.co.archive_asia.onedaycouplediary.firestore.response.ResultStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository {

    private val userCollection: CollectionReference by lazy {
        Firebase.firestore.collection(USERS)
    }

    suspend fun addNewUser(user: User): MutableLiveData<ResultStatus<User>> =
        withContext(Dispatchers.IO) {

            val resultStatus = MutableLiveData<ResultStatus<User>>()
            userCollection.document(user.id).set(user)

                .addOnSuccessListener {
                    resultStatus.value = ResultStatus.Success(user)
                }

                .addOnFailureListener { exception ->
                    val errorMessage = exception.message.toString()
                    resultStatus.value = ResultStatus.Error(message = errorMessage)
                }

            resultStatus
        }


}