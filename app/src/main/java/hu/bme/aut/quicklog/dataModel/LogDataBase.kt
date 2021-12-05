package hu.bme.aut.quicklog.dataModel

import android.content.Context
import androidx.room.*
import java.util.*

@Database(entities=[LogItem::class], version = 1, exportSchema = false)
@TypeConverters(value = [LogItem::class])
abstract class LogDataBase : RoomDatabase() {
    abstract fun logItemDao(): LogItemDao

    companion object {
        fun getDatabase(applicationContext: Context): LogDataBase {
            return Room.databaseBuilder(
                applicationContext,
                LogDataBase::class.java,
                "log-list"
            ).build();
        }
    }
}