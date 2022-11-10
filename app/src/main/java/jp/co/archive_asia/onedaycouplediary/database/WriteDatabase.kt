package jp.co.archive_asia.onedaycouplediary.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import jp.co.archive_asia.onedaycouplediary.model.Write

@Database(entities = [Write::class], version = 1, exportSchema = false)
abstract class WriteDatabase : RoomDatabase() {

    abstract fun writeDao(): WriteDao

    companion object {
        @Volatile
        private var INSTANCE: WriteDatabase? = null

        fun getDatabase(context: Context): WriteDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                WriteDatabase::class.java, "write_database"
            ).build()
    }
}