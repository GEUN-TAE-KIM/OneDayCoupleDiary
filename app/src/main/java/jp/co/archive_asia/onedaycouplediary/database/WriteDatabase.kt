package jp.co.archive_asia.onedaycouplediary.database

import androidx.room.Database
import androidx.room.RoomDatabase
import jp.co.archive_asia.onedaycouplediary.model.Write

@Database(entities = [Write::class], version = 1, exportSchema = false)
abstract class WriteDatabase : RoomDatabase() {

    abstract fun writeDao(): WriteDao
}