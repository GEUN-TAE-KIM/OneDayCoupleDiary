package jp.co.archive_asia.onedaycouplediary.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import jp.co.archive_asia.onedaycouplediary.model.Diary

@Database(entities = [Diary::class], version = 1, exportSchema = false)
abstract class DiaryDatabase : RoomDatabase() {

    abstract fun writeDao(): DiaryDao

    companion object {
        @Volatile
        private var INSTANCE: DiaryDatabase? = null

        fun getDatabase(context: Context): DiaryDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                DiaryDatabase::class.java, "write_database"
            ).build()
    }
}