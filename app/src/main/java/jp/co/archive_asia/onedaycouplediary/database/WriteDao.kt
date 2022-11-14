package jp.co.archive_asia.onedaycouplediary.database

import androidx.room.*
import jp.co.archive_asia.onedaycouplediary.model.Write
import kotlinx.coroutines.flow.Flow
import java.time.Month
import java.util.*

@Dao
interface WriteDao {

    @Query("SELECT * FROM Write ORDER BY date DESC, id DESC")
    fun getAllData(): Flow<List<Write>>

    @Query("SELECT * FROM Write WHERE title LIKE :searchQuery OR content LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<Write>>

    // 일 일기 리스트
    @Query("SELECT * FROM Write WHERE date = :date ORDER BY id DESC")
    suspend fun readDateData(date: String): List<Write>

    // TODO: 1. 월 일기 리스트 가져오는 함
    // 2022/11
    // 2022/11/1 <= 2022/12/1
    @Query("SELECT * FROM Write WHERE date BETWEEN :pre AND :next ORDER BY id DESC")
    suspend fun selectDateData(pre: Long, next: Long): List<Write>

    /*@Query("SELECT * FROM Write WHERE dateMonth = :dateMonth ORDER BY id DESC")
    suspend fun selectDateData(dateMonth: Month): List<Write>*/

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addData(write: Write)

    @Update
    suspend fun updateData(write: Write)

    @Delete
    suspend fun deleteData(write: Write)

}