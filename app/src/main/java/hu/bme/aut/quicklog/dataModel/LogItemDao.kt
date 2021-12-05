package hu.bme.aut.quicklog.dataModel

import androidx.room.*

@Dao
interface LogItemDao {
    @Query("SELECT * FROM logitem ORDER BY date")
    fun getAll(): List<LogItem>

    @Insert
    fun insert(logItem: LogItem): Long

    @Update
    fun update(logItem: LogItem)

    @Delete
    fun delete(logItem: LogItem)

}