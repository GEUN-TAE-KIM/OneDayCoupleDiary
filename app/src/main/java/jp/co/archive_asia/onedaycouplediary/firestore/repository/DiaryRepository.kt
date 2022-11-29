package jp.co.archive_asia.onedaycouplediary.firestore.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import jp.co.archive_asia.onedaycouplediary.firestore.Constants.DIARY
import jp.co.archive_asia.onedaycouplediary.firestore.Constants.USER_ID
import jp.co.archive_asia.onedaycouplediary.firestore.response.EmptyResult
import jp.co.archive_asia.onedaycouplediary.firestore.response.ResultStatus
import jp.co.archive_asia.onedaycouplediary.model.Diary

typealias ResultCallback<T> = (ResultStatus<T>) -> Unit
typealias EmptyCallback = (EmptyResult) -> Unit

class DiaryRepository {

    private val diaryCollection: CollectionReference by lazy {
        Firebase.firestore.collection(DIARY)
    }

    fun addData(diary: Diary, result: ResultCallback<Diary>) {

        diaryCollection.add(diary)
            .addOnSuccessListener { diaryDoc ->
                result(ResultStatus.Success(diary))

            }
            .addOnFailureListener { exception ->
                val errorMessage = exception.message.toString()
                result(ResultStatus.Error(errorMessage))
            }

    }

    fun updateData(diary: Diary, result: ResultCallback<Diary>) {

        val diaryId = diary.user_id

        diaryCollection.document(diaryId)
            .set(diary)
            .addOnSuccessListener {
                result(ResultStatus.Success(diary))
            }
            .addOnFailureListener { exception ->
                val errorMessage = exception.message.toString()
                result(ResultStatus.Error(errorMessage))
            }

    }

    fun deleteData(diary: Diary, result: EmptyCallback) {

        diaryCollection.document(diary.toString()).delete()
            .addOnSuccessListener {
                result(EmptyResult.Success)
            }
            .addOnFailureListener { exception ->
                val errorMessage = exception.message.toString()
                result(EmptyResult.Error(errorMessage))
            }

    }

    // 작성한 전체 일기 취득
    fun getAllDiary(uid: String, result: ResultCallback<List<Diary>>) {
        diaryCollection
            //whereEqualTo는 비교,결과를 검색
            .whereEqualTo(USER_ID, uid)
            //.orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    result(ResultStatus.Error(error.message.toString()))
                    return@addSnapshotListener
                }
                value?.let { result ->
                    val diary = result.toObjects(Diary::class.java)
                    result(ResultStatus.Success(diary))
                } ?: result(ResultStatus.Error("errorMessage"))
            }
    }

    fun getMonthlyDiary(uid: String, result: ResultCallback<List<Diary>>) {
        // Timestamp
        diaryCollection

            //whereEqualTo는 비교,결과를 검색
            .whereEqualTo(USER_ID, uid)

            //.orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                val diary = result.toObjects(Diary::class.java).toMutableList()
                result(ResultStatus.Success(diary))
            }
            .addOnFailureListener { exception ->
                val errorMessage = exception.message.toString()
                result(ResultStatus.Error(errorMessage))
            }
    }

}