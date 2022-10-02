package jp.co.archive_asia.onedaycouplediary.repository

import jp.co.archive_asia.onedaycouplediary.database.WriteDao
import jp.co.archive_asia.onedaycouplediary.model.Write
import kotlinx.coroutines.flow.Flow

class WriteRepository(private val writeDao: WriteDao) {

    val getAllData: Flow<List<Write>> = writeDao.getAllData()

    suspend fun addData(write: Write) {
        writeDao.addData(write)
    }

    suspend fun updateData(write: Write) {
        writeDao.updateData(write)
    }

    suspend fun deleteData(write: Write) {
        writeDao.deleteData(write)
    }

    fun searchDatabase(searchQuery: String): Flow<List<Write>> {
        return writeDao.searchDatabase(searchQuery)
    }

    fun readDateData(year: Int, month: Int, day: Int): List<Write> {
        return writeDao.readDateData(year, month, day)
    }

}