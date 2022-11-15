package jp.co.archive_asia.onedaycouplediary.repository

import jp.co.archive_asia.onedaycouplediary.database.DiaryDao
import jp.co.archive_asia.onedaycouplediary.model.Diary
import kotlinx.coroutines.flow.Flow

class DiaryRepository(private val diaryDao: DiaryDao) {

    // val getAllData: Flow<List<Write>> = writeDao.getAllData()

    suspend fun addData(diary: Diary) {
        diaryDao.addData(diary)
    }

    suspend fun updateData(diary: Diary) {
        diaryDao.updateData(diary)
    }

    suspend fun deleteData(diary: Diary) {
        diaryDao.deleteData(diary)
    }

    suspend fun readDateData(date: String): List<Diary> {
        return diaryDao.readDateData(date)
    }

    suspend fun selectDateData(pre: Long, next: Long): List<Diary> {
        return diaryDao.selectDateData(pre, next)
    }

    fun searchDatabase(searchQuery: String): Flow<List<Diary>> {
        return diaryDao.searchDatabase(searchQuery)
    }


}