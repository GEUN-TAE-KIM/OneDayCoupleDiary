package jp.co.archive_asia.onedaycouplediary.database

import androidx.room.*
import jp.co.archive_asia.onedaycouplediary.model.Diary
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {

    @Query("SELECT * FROM Diary ORDER BY date DESC, id DESC")
    fun getAllData(): Flow<List<Diary>>

    @Query("SELECT * FROM Diary WHERE title LIKE :searchQuery OR content LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<Diary>>

    // 일 일기 리스트
    @Query("SELECT * FROM Diary WHERE date = :date ORDER BY id DESC")
    suspend fun readDateData(date: String): List<Diary>

    // TODO: 1. 월 일기 리스트 가져오는 함
    // 2022/11
    // 2022/11/1 <= 2022/12/1
    @Query("SELECT * FROM Diary WHERE date BETWEEN :pre AND :next ORDER BY id DESC")
    suspend fun selectDateData(pre: Long, next: Long): List<Diary>

    /*@Query("SELECT * FROM Write WHERE dateMonth = :dateMonth ORDER BY id DESC")
    suspend fun selectDateData(dateMonth: Month): List<Write>*/

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addData(diary: Diary)

    @Update
    suspend fun updateData(diary: Diary)

    @Delete
    suspend fun deleteData(diary: Diary)

}