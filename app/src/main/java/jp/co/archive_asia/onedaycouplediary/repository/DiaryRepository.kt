package jp.co.archive_asia.onedaycouplediary.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import jp.co.archive_asia.onedaycouplediary.firestore.Constants.DIARY
import jp.co.archive_asia.onedaycouplediary.firestore.Constants.USER_ID
import jp.co.archive_asia.onedaycouplediary.firestore.response.EmptyResult
import jp.co.archive_asia.onedaycouplediary.firestore.response.ResultStatus
import jp.co.archive_asia.onedaycouplediary.model.Diary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DiaryRepository {

    private val diaryCollection: CollectionReference by lazy {
        Firebase.firestore.collection(DIARY)
    }

    private val currentUserUid = Firebase.auth.currentUser!!.uid

    suspend fun addData(diary: Diary): MutableLiveData<ResultStatus<Diary>> =
        withContext(Dispatchers.IO) {

            val resultStatus = MutableLiveData<ResultStatus<Diary>>()
            diaryCollection.add(diary)

                .addOnSuccessListener { diaryDoc ->
                    val id = diaryDoc.id
                    diary.id = id

                    CoroutineScope(Dispatchers.IO).launch {
                        diaryCollection.document(id).set(diary)
                    }
                    resultStatus.value = ResultStatus.Success(diary)
                }

                .addOnFailureListener { exception ->
                    val errorMessage = exception.message.toString()
                    resultStatus.value = ResultStatus.Error(errorMessage)
                }

            resultStatus
        }

    suspend fun updateData(diary: Diary): MutableLiveData<ResultStatus<Diary>> =
        withContext(Dispatchers.IO) {

            val resultStatus = MutableLiveData<ResultStatus<Diary>>()
            val diaryId = diary.id

            diaryCollection.document(diaryId)

                .set(diary)

                .addOnSuccessListener {
                    resultStatus.value = ResultStatus.Success(diary)
                }

                .addOnFailureListener { exception ->
                    val errorMessage = exception.message.toString()
                    resultStatus.value = ResultStatus.Error(errorMessage)
                }
            resultStatus
        }

    suspend fun deleteData(diary: Diary): MutableLiveData<EmptyResult> =
        withContext(Dispatchers.IO) {
            val response = MutableLiveData<EmptyResult>()

            diaryCollection.document(diary.toString()).delete()

                .addOnSuccessListener {
                    response.value = EmptyResult.Success
                }
                .addOnFailureListener { exception ->
                    val errorMessage = exception.message.toString()
                    response.value = EmptyResult.Error(errorMessage)
                }

            response
        }

    suspend fun selectDateData(pre: Long, next: Long):
            MutableLiveData<ResultStatus<MutableList<Diary>>> =
        withContext(Dispatchers.IO) {
            val resultStatus = MutableLiveData<ResultStatus<MutableList<Diary>>>()

            diaryCollection

                //whereEqualTo는 비교,결과를 검색
                /*.whereEqualTo(USER_ID, currentUserUid)
                .orderBy(USER_ID, Query.Direction.DESCENDING)*/
                .orderBy(USER_ID, Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { result ->
                    val diary = result.toObjects(Diary::class.java).toMutableList()
                    resultStatus.value = ResultStatus.Success(diary)
                }
                .addOnFailureListener { exception ->
                    val errorMessage = exception.message.toString()
                    resultStatus.value = ResultStatus.Error(errorMessage)
                }

            resultStatus
        }

   /* suspend fun selectDateData2(pre: Long, next: Long): List<Diary> {
        return diaryDao.selectDateData(pre, next)
    }*/


}