package hu.bme.aut.quicklog.dataModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.Date

@Entity(tableName = "logitem")
data class LogItem(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id : Long? = null,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "rating") var rating: Int,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "date") var date: Date
){
    companion object {
        @JvmStatic
        @TypeConverter
        fun fromTimestamp(value: Long): Date {
            return Date(value)
        }

        @JvmStatic
        @TypeConverter
        fun dateToTimestamp(date: Date): Long {
            return date.time
        }
    }
}
