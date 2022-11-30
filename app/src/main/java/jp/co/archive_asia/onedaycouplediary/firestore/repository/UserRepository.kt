package jp.co.archive_asia.onedaycouplediary.firestore.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import jp.co.archive_asia.onedaycouplediary.firestore.Constants.DIARY
import jp.co.archive_asia.onedaycouplediary.firestore.Constants.USERS
import jp.co.archive_asia.onedaycouplediary.firestore.Constants.USER_ID
import jp.co.archive_asia.onedaycouplediary.firestore.models.User
import jp.co.archive_asia.onedaycouplediary.firestore.response.EmptyResult

typealias AuthCallback = (EmptyResult) -> Unit

class UserRepository {

    private val auth = Firebase.auth
    private val userCollection: CollectionReference by lazy {
        Firebase.firestore.collection(USERS)
    }
    private val diaryCollection: CollectionReference by lazy {
        Firebase.firestore.collection(DIARY)
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

    /**
     * 匿名ログイン
     */
    fun signInAnonymously(result: AuthCallback) {
        auth.signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = task.result.user!!.uid
                    val user = User(email = "匿名") // TODO
                    userCollection.document(uid).set(user)
                        .addOnCompleteListener { firestoreTask ->
                            if (firestoreTask.isSuccessful) {
                                result(EmptyResult.Success)
                            } else {
                                result(EmptyResult.Error(firestoreTask.exception?.message.toString()))
                            }
                        }
                } else {
                    result(EmptyResult.Error(getErrorMessage(task)))
                }
            }
    }

    /**
     * 匿名ユーザーの会員登録
     */
    fun signUpEmailFromAnonymous(email: String, password: String, result: AuthCallback) {
        val credential = EmailAuthProvider.getCredential(email, password)
        auth.currentUser!!.linkWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = task.result.user!!.uid
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
                    result(EmptyResult.Error(getErrorMessage(task)))
                }
            }
    }

    fun logout(result: AuthCallback) {
        if (auth.currentUser?.isAnonymous == true) {
            deleteAnonymous(result)
        } else {
            auth.signOut()
            result(EmptyResult.Success)
        }
    }

    private fun deleteAnonymous(result: AuthCallback) {
        // 익명 계정 삭제
        currentUser?.delete()?.addOnCompleteListener {
            if (!it.isSuccessful) {
                // 익명 계정 삭제 실패
                result(EmptyResult.Error(it.exception?.message.toString()))
                return@addOnCompleteListener
            }
            val uid = Firebase.auth.currentUser?.uid ?: ""
            val batch = Firebase.firestore.batch()
            // 삭제할 User 도큐먼
            val userDocument = userCollection.document(uid)
            // User 도큐먼트 배치 삭제 등록
            batch.delete(userDocument)
            // 파이어스토어 Diary 삭제
            diaryCollection
                .whereEqualTo(USER_ID, uid)
                .get()
                .addOnCompleteListener { diarySnapshot ->
                    if (!diarySnapshot.isSuccessful) {
                        // 다이어리 취득 실패
                        result(EmptyResult.Error(it.exception?.message.toString()))
                        return@addOnCompleteListener
                    }
                    diarySnapshot.result.documents.forEach { documentSnapshot ->
                        // 다이어리 도큐먼트 배치 삭제 등록
                        batch.delete(documentSnapshot.reference)
                    }
                    // 배치 커밋(실행)
                    batch.commit()
                        .addOnCompleteListener { deleteTask ->
                            if (!deleteTask.isSuccessful) {
                                result(EmptyResult.Error(deleteTask.exception?.message.toString()))
                                return@addOnCompleteListener
                            }
                            result(EmptyResult.Success)
                        }
                }
        }
    }

    private fun getErrorMessage(task: Task<AuthResult>): String {
        return when (task.exception) {
            is FirebaseAuthInvalidCredentialsException -> {
                "正しいメールを入力してください"
            }
            is FirebaseAuthUserCollisionException -> {
                "すでに登録されているメールです"
            }
            is FirebaseAuthWeakPasswordException -> {
                "パスワードは６桁以上を入力してください"
            }
            else -> {
                "予想せぬエラーが発生しました"
            }
        }
    }
}