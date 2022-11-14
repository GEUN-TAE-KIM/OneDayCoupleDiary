package jp.co.archive_asia.onedaycouplediary.repository

import jp.co.archive_asia.onedaycouplediary.database.WriteDao
import jp.co.archive_asia.onedaycouplediary.model.Write
import kotlinx.coroutines.flow.Flow
import java.text.DateFormat
import java.time.LocalDate
import java.time.Month
import java.util.*

class WriteRepository(private val writeDao: WriteDao) {

    // val getAllData: Flow<List<Write>> = writeDao.getAllData()

    suspend fun addData(write: Write) {
        writeDao.addData(write)
    }

    suspend fun updateData(write: Write) {
        writeDao.updateData(write)
    }

    suspend fun deleteData(write: Write) {
        writeDao.deleteData(write)
    }

    suspend fun readDateData(date: String): List<Write> {
        return writeDao.readDateData(date)
    }

    suspend fun selectDateData(pre: Long, next: Long): List<Write> {
        return writeDao.selectDateData(pre, next)
    }

    fun searchDatabase(searchQuery: String): Flow<List<Write>> {
        return writeDao.searchDatabase(searchQuery)
    }


}