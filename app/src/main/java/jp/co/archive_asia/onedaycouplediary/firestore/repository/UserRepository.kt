package jp.co.archive_asia.onedaycouplediary.firestore.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import jp.co.archive_asia.onedaycouplediary.firestore.Constants.USERS
import jp.co.archive_asia.onedaycouplediary.firestore.models.User
import jp.co.archive_asia.onedaycouplediary.firestore.response.EmptyResult

typealias AuthCallback = (EmptyResult) -> Unit

class UserRepository {

    private val auth = Firebase.auth
    private val userCollection: CollectionReference by lazy {
        Firebase.firestore.collection(USERS)
    }

    val currentUser get() = auth.currentUser

    fun emailLogin(email: String, password: String, result: AuthCallback) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    result(EmptyResult.Success)
                } else {
                    val errorMessage = getErrorMessage(task)
                    result(EmptyResult.Error(errorMessage))
                }
            }
    }

    fun signUpEmail(email: String, password: String, result: AuthCallback) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {
                    val uid = authTask.result.user!!.uid
                    val user = User(email = email)
                    userCollection.document(uid).set(user)
                        .addOnCompleteListener { firestoreTask ->
                            if (firestoreTask.isSuccessful) {
                                result(EmptyResult.Success)
                            } else {
                                result(EmptyResult.Error(firestoreTask.exception?.message.toString()))
                            }
                        }
                } else {
                    result(EmptyResult.Error(getErrorMessage(authTask)))
                }
            }
    }

    private fun getErrorMessage(task: Task<AuthResult>): String {
        return when(task.exception) {
            is FirebaseAuthInvalidCredentialsException -> { "正しいメールを入力してください" }
            is FirebaseAuthUserCollisionException -> { "すでに登録されているメールです" }
            is FirebaseAuthWeakPasswordException -> { "パスワードは６桁以上を入力してください" }
            else -> { "予想せぬエラーが発生しました" }
        }
    }
}