package jp.co.archive_asia.onedaycouplediary.database

import androidx.room.*
import jp.co.archive_asia.onedaycouplediary.model.Write
import kotlinx.coroutines.flow.Flow

@Dao
interface WriteDao {

    @Query("SELECT * FROM Write ORDER BY id DESC")
    fun getAllData(): Flow<List<Write>>

    @Query("SELECT * FROM Write WHERE title LIKE :searchQuery OR content LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<Write>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addData(write: Write)

    @Update
    suspend fun updateData(write: Write)

    @Delete
    suspend fun deleteData(write: Write)

}