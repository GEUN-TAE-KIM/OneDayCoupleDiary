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
        // 접근 가능한 변수의 값을 캐시를 통해 하지 않고 쓰레드가 직접 메모리에 접근하는 것
        @Volatile
        private var INSTANCE: WriteDatabase? = null

        fun getDatabase(context: Context): WriteDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            // 스레드가 호출 시 동기화된 잠금을 획득 , 잠금을 해제하지 않는 한 다른 스레드는 호출 권한이 없음
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WriteDatabase::class.java,
                    "write_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}