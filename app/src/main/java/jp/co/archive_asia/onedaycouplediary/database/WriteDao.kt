package jp.co.archive_asia.onedaycouplediary.database

import androidx.room.*
import jp.co.archive_asia.onedaycouplediary.model.Write
import kotlinx.coroutines.flow.Flow

@Dao
interface WriteDao {

    @Query("SELECT * FROM Write ORDER BY year DESC, month DESC, day DESC, id DESC")
    fun getAllData(): Flow<List<Write>>

    @Query("SELECT * FROM Write WHERE year = :year AND month = :month AND day = :day ORDER BY id DESC")
    fun readDateData(year: Int, month: Int, day: Int): List<Write>

    @Query("SELECT * FROM Write WHERE title LIKE :searchQuery OR content LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<Write>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addData(write: Write)

    @Update
    suspend fun updateData(write: Write)

    @Delete
    suspend fun deleteData(write: Write)

}