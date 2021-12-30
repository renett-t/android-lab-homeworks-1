package ru.itis.renett.testapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.itis.renett.testapp.entity.Task

@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(DateConverters::class)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        private const val DATABASE_NAME = "task.db"
        private lateinit var instance: TaskDatabase
        private var isInitialized: Boolean = false

        fun getInstance(context: Context): TaskDatabase {
            if (!isInitialized) {
                instance = Room.databaseBuilder(context, TaskDatabase::class.java, DATABASE_NAME)
                    .build()
                isInitialized = true
            }

            return instance
        }
    }
}
