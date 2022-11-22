package jp.co.archive_asia.onedaycouplediary.firestore

import android.util.Log
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import jp.co.archive_asia.onedaycouplediary.firestore.models.User
import jp.co.archive_asia.onedaycouplediary.model.Diary
import jp.co.archive_asia.onedaycouplediary.view.auth.JoinFragment
import jp.co.archive_asia.onedaycouplediary.view.auth.LoginFragment
import jp.co.archive_asia.onedaycouplediary.view.fragment.WriteDiaryFragment

class FirestoreClass {

    private val mFirestore = FirebaseFirestore.getInstance()

    // 유저 등록
    fun registerUser(fragment: JoinFragment, userInfo: User) {

        mFirestore.collection(Constants.USERS)
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())

    }

    // 유저 데이터 가져오기
    fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }

    // 유저 상세 데이터
    fun getUserDetails(fragment: Fragment) {

        mFirestore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                Log.i(fragment.javaClass.simpleName, document.toString())

                val user = document.toObject(User::class.java)

                when (fragment) {
                    is LoginFragment -> {
                        if (user != null) {
                            fragment.userLoggedInSuccess(user)
                        }
                    }
                }
            }
            .addOnFailureListener { e ->

            }
    }

    // 데이터 업로드
    fun uploadDiaryDetails(fragment: WriteDiaryFragment, diaryInfo: Diary) {
        mFirestore.collection(Constants.DIARY)
            .document()
            .set(diaryInfo, SetOptions.merge())
            .addOnSuccessListener {
                Log.i(fragment.javaClass.simpleName, diaryInfo.toString())
            }
            .addOnFailureListener { e ->
                Log.e(
                    fragment.javaClass.simpleName,
                    "Error",
                    e
                )
            }
    }

    // 데이터 읽기
    fun getDiaryList(fragment: Fragment) {
        mFirestore.collection(Constants.DIARY)
            .whereEqualTo(Constants.USER_ID, getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                Log.e("Diary List",document.documents.toString())
                val diaryList: ArrayList<Diary> = ArrayList()

                for (i in document.documents) {
                    val diary = i.toObject(Diary::class.java)
                    diary!!.diary_id = i.id

                    diaryList.add(diary)
                }

                when(fragment) {
                    is WriteDiaryFragment -> {
                        fragment.successDiaryListFromFireStore(diaryList)
                    }
                }
            }
    }
}







































