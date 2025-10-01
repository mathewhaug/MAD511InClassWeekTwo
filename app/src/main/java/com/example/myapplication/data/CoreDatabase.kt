package com.example.myapplication.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.model.Note


@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class CoreDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: CoreDatabase? = null

        fun getDatabase(context: Context): CoreDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoreDatabase::class.java,
                    "core_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

